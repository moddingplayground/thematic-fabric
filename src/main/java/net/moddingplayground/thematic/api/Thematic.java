package net.moddingplayground.thematic.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.impl.block.ThematicBlocks;
import net.moddingplayground.thematic.impl.item.Themed;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.*;

public interface Thematic {
    String MOD_ID   = "thematic";
    String MOD_NAME = "Thematic";

    ItemGroup ITEM_GROUP =
        Util.make(TabbedItemGroup.builder(), builder -> {
                for (Theme theme : Theme.values()) {
                    String id = theme.getId();
                    Theme.Colors colors = theme.getColors();
                    Style style = Style.EMPTY.withColor(colors.getTitle());
                    builder.tab(Tab.builder()
                                   .displayText(tab -> createDisplayText(tab.getGroup(), tab).shallowCopy().fillStyle(style))
                                   .predicate((group, item) -> Theme.tabPredicate(theme, item))
                                   .build(id, GUIIcon.of(() -> new ItemStack(theme.getItem())))
                    );
                }
            })
            .defaultPredicate((group, item) -> item instanceof Themed)
            .build(new Identifier(MOD_ID, "item_group"), group -> GUIIcon.of(() -> new ItemStack(ThematicBlocks.FIRST)));

    static Identifier defaultedId(String id) {
        return id.indexOf(Identifier.NAMESPACE_SEPARATOR) != -1 ? new Identifier(id) : new Identifier(Thematic.MOD_ID, id);
    }
}
