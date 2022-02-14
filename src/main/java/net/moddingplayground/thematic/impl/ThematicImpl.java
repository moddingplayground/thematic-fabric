package net.moddingplayground.thematic.impl;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.ThematicEntrypoint;
import net.moddingplayground.thematic.api.item.Themed;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.ThemeColors;
import net.moddingplayground.thematic.impl.item.ThematicItems;
import net.moddingplayground.thematic.impl.theme.BuiltinDecoratablesImpl;
import net.moddingplayground.thematic.impl.theme.BuiltinThemesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.*;

public class ThematicImpl implements ModInitializer, Thematic {
    private static ThematicImpl instance = null;

    protected final Logger logger;
    protected TabbedItemGroup itemGroup = null;

    public ThematicImpl() {
        this.logger = LoggerFactory.getLogger(MOD_ID);
        instance = this;
    }

    public TabbedItemGroup getItemGroup() {
        return this.itemGroup;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        this.logger.info("Initializing {}", MOD_NAME);

        Reflection.initialize(ThematicItems.class, BuiltinThemesImpl.class, BuiltinDecoratablesImpl.class);

        FabricLoader loader = FabricLoader.getInstance();
        for (EntrypointContainer<ThematicEntrypoint> container : loader.getEntrypointContainers(Thematic.MOD_ID, ThematicEntrypoint.class)) {
            ThematicEntrypoint entrypoint = container.getEntrypoint();
            entrypoint.onInitializeThematic();
        }

        Thematic.DECORATABLE_REGISTRY.forEach(Decoratable::register);

        this.itemGroup = Util.make(TabbedItemGroup.builder(), builder -> {
                for (Theme theme : Thematic.THEME_REGISTRY) {
                    Identifier id = theme.getId();
                    ThemeColors colors = theme.getColors();
                    Style style = Style.EMPTY.withColor(colors.getTitle());
                    builder.tab(Tab.builder()
                                   .displayText(tab -> createDisplayText(tab.getGroup(), tab).shallowCopy().fillStyle(style))
                                   .predicate((group, item) -> Theme.tabPredicate(theme, item))
                                   .build(id.toString(), GUIIcon.of(() -> new ItemStack(theme.getItem())))
                    );
                }
            })
            .defaultPredicate((group, item) -> item instanceof Themed)
            .build(new Identifier(MOD_ID, "themes"), g -> GUIIcon.of(() -> new Identifier(MOD_ID, "icon.png")));
    }

    public static ThematicImpl getInstance() {
        return instance;
    }
}
