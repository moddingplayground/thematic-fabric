package net.moddingplayground.thematic.impl.block;

import com.google.common.reflect.Reflection;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.ThematicEntrypoint;
import net.moddingplayground.thematic.api.registry.DecoratableRegistry;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.DefaultDecoratables;
import net.moddingplayground.thematic.api.theme.Theme;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.function.Function;

@SuppressWarnings("UnstableApiUsage")
public class ThematicBlocks {
    static {
        Reflection.initialize(DefaultDecoratables.class);

        FabricLoader loader = FabricLoader.getInstance();
        for (EntrypointContainer<ThematicEntrypoint> container : loader.getEntrypointContainers(Thematic.MOD_ID, ThematicEntrypoint.class)) {
            ThematicEntrypoint entrypoint = container.getEntrypoint();
            entrypoint.onInitializeThematic();
        }

        ThematicBlocks.forEach((theme, decoratable, air) -> {
            Block block = register(decoratable.format(theme), decoratable.block(theme), b -> decoratable.item(b, theme));
            decoratable.getPostRegister().apply(theme, decoratable, block);
        });
    }

    public static final Block FIRST = Registry.BLOCK.stream().filter(block -> Registry.BLOCK.getId(block).getNamespace().equals(Thematic.MOD_ID))
                                                    .toList().get(0);

    public static void forEach(TriConsumer<Theme, Decoratable, Block> action) {
        DecoratableRegistry.INSTANCE.forEach(decoratable -> Theme.forEach(theme -> {
            Block block = theme.get(decoratable);
            action.accept(theme, decoratable, block);
        }));
    }

    private static Block register(String id, Block block, Function<Block, Item> item) {
        Identifier identifier = Thematic.defaultedId(id);
        if (item != null) Registry.register(Registry.ITEM, identifier, item.apply(block));
        return Registry.register(Registry.BLOCK, identifier, block);
    }
}
