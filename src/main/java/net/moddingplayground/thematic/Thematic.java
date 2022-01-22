package net.moddingplayground.thematic;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.block.ThematicBlocks;
import net.moddingplayground.thematic.item.ThematicItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thematic implements ModInitializer {
    public static final String    MOD_ID     = "thematic";
    public static final String    MOD_NAME   = "Thematic";
    public static final Logger    LOGGER     = LoggerFactory.getLogger(MOD_ID);
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(ThematicBlocks.FIRST));

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);
        Reflection.initialize(ThematicItems.class, ThematicBlocks.class);
        LOGGER.info("Initialized {}", MOD_NAME);
    }

    public static Identifier defaultedId(String id) {
        return id.indexOf(Identifier.NAMESPACE_SEPARATOR) != -1 ? new Identifier(id) : new Identifier(Thematic.MOD_ID, id);
    }
}
