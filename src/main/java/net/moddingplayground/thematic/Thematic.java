package net.moddingplayground.thematic;

import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.gui.itemgroup.Tab;
import net.moddingplayground.frame.api.gui.itemgroup.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.block.ThematicBlocks;
import net.moddingplayground.thematic.item.Themed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.moddingplayground.frame.api.gui.itemgroup.Tab.*;

@SuppressWarnings("UnstableApiUsage")
public class Thematic implements ModInitializer {
    public static final String    MOD_ID     = "thematic";
    public static final String    MOD_NAME   = "Thematic";
    public static final Logger    LOGGER     = LoggerFactory.getLogger(MOD_ID);

    public static final ItemGroup ITEM_GROUP =
        Util.make(TabbedItemGroup.builder(), builder -> {
                for (Theme theme : Theme.values()) {
                    builder.tab(
                        Tab.builder()
                           .displayText(tab -> createDisplayText(tab.getGroup(), tab).shallowCopy().fillStyle(Style.EMPTY.withColor(theme.getTooltipColor())))
                           .predicate((g, item) -> item instanceof Themed themed && themed.getTheme() == theme && item != theme.getItem())
                           .build(theme.getId(), GUIIcon.of(() -> new ItemStack(theme.getItem())))
                    );
                }
            })
            .defaultPredicate((g, item) -> item instanceof Themed)
            .build(new Identifier(MOD_ID, "item_group"), g -> GUIIcon.of(() -> new ItemStack(ThematicBlocks.FIRST)));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);
        Reflection.initialize(ThematicBlocks.class);
        LOGGER.info("Initialized {}", MOD_NAME);
    }

    public static Identifier defaultedId(String id) {
        return id.indexOf(Identifier.NAMESPACE_SEPARATOR) != -1 ? new Identifier(id) : new Identifier(Thematic.MOD_ID, id);
    }
}
