package net.moddingplayground.thematic.api.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.block.ThematicBlocks;
import net.moddingplayground.thematic.api.registry.ThematicRegistry;
import net.moddingplayground.thematic.api.tag.ThematicItemTags;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.ThemeColors;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.*;

public interface ThematicItemGroups {
    ItemGroup THEMES = Util.make(TabbedItemGroup.builder(), builder -> {
                               for (Theme theme : ThematicRegistry.THEME) {
                                   Identifier id = theme.getId();
                                   ThemeColors colors = theme.getColors();
                                   Style style = Style.EMPTY.withColor(colors.getDark());
                                   builder.tab(Tab.builder()
                                                  .displayText(tab -> createDisplayText(tab.getGroup(), tab).copy().fillStyle(style))
                                                  .predicate((group, item) -> Theme.tabPredicate(theme, item))
                                                  .build(id.toString(), GUIIcon.of(() -> new ItemStack(theme.getItem())))
                                   );
                               }
                           })
                           .defaultPredicate((group, item) -> item.getDefaultStack().isIn(ThematicItemTags.ITEM_GROUP_ALL_TAB_ITEMS))
                           .build(new Identifier(Thematic.MOD_ID, "themes"), g -> GUIIcon.of(() -> new ItemStack(ThematicBlocks.DECORATORS_TABLE)));
}
