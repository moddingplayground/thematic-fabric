package net.moddingplayground.thematic.api.tag;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.api.Thematic;

/**
 * Item tags provided by Thematic.
 */
public interface ThematicItemTags {
    TagKey<Item> ITEM_GROUP_ALL_TAB_ITEMS = register("item_group_all_tab_items");
    TagKey<Item> RUSTIC_TREASURE_ITEMS = register("rustic_treasure_items");

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Thematic.MOD_ID, id));
    }
}
