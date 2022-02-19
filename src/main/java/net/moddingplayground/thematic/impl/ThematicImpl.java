package net.moddingplayground.thematic.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.frame.api.util.InitializationLogger;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.ThematicEntrypoint;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.item.ThematicItems;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeSerializer;
import net.moddingplayground.thematic.api.recipe.ThematicRecipeType;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.screen.ThematicScreenHandlerType;
import net.moddingplayground.thematic.api.stat.ThematicStats;
import net.moddingplayground.thematic.api.tag.ThematicItemTags;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.ThemeColors;

import java.util.List;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.*;

public class ThematicImpl implements ModInitializer, Thematic {
    private static ThematicImpl instance = null;

    protected final InitializationLogger initializer;
    protected TabbedItemGroup itemGroup = null;

    public ThematicImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME);
        instance = this;
    }

    public TabbedItemGroup getItemGroup() {
        return this.itemGroup;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        this.initializer.start();

        // initialize other mods' implementations
        FabricLoader loader = FabricLoader.getInstance();
        List<EntrypointContainer<ThematicEntrypoint>> entrypoints = loader.getEntrypointContainers(Thematic.MOD_ID, ThematicEntrypoint.class);
        if (!entrypoints.isEmpty()) {
            InitializationLogger entrypointLogger = new InitializationLogger(LOGGER, "%s-ENTRYPOINTS".formatted(MOD_NAME));
            entrypointLogger.start();

            for (EntrypointContainer<ThematicEntrypoint> container : entrypoints) {
                ModContainer provider = container.getProvider();
                ModMetadata metadata = provider.getMetadata();
                LOGGER.info("   Initializing {}-{}", MOD_ID, metadata.getId());

                ThematicEntrypoint entrypoint = container.getEntrypoint();
                entrypoint.onInitializeThematic();
            }

            entrypointLogger.finish();
        }

        // initialize the item group
        this.itemGroup = Util.make(TabbedItemGroup.builder(), builder -> {
                                 for (Theme theme : ThematicRegistry.THEME) {
                                     Identifier id = theme.getId();
                                     ThemeColors colors = theme.getColors();
                                     Style style = Style.EMPTY.withColor(colors.getDark());
                                     builder.tab(Tab.builder()
                                                    .displayText(tab -> createDisplayText(tab.getGroup(), tab).shallowCopy().fillStyle(style))
                                                    .predicate((group, item) -> Theme.tabPredicate(theme, item))
                                                    .build(id.toString(), GUIIcon.of(() -> new ItemStack(theme.getItem())))
                                     );
                                 }
                             })
                             .defaultPredicate((group, item) -> ThematicItemTags.ITEM_GROUP_ALL_TAB_ITEMS.contains(item))
                             .build(new Identifier(MOD_ID, "themes"), g -> GUIIcon.of(() -> new ItemStack(ThematicBlocks.DECORATORS_TABLE)));

        // register base objects
        Reflection.initialize(
            ThematicStats.class,
            ThematicRecipeType.class, ThematicRecipeSerializer.class,
            ThematicScreenHandlerType.class,
            ThematicBlocks.class,
            ThematicItems.class
        );

        ThematicBlocks.onInitialize();

        // register all decoratables
        ThematicRegistry.DECORATABLE.forEach(Decoratable::register);

        this.initializer.finish();
    }

    public static ThematicImpl getInstance() {
        return instance;
    }
}
