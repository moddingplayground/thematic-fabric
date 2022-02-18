package net.moddingplayground.thematic.api.tag;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;

/**
 * Item tags provided by Thematic.
 */
public interface ThematicItemTags {
    Tag.Identified<Item> DYES = register("dyes");
    Tag.Identified<Item> RUSTIC_TREASURE_ITEMS = register("rustic_treasure_items");

    private static Tag.Identified<Item> register(String id) {
        return TagFactory.ITEM.create(new Identifier(Thematic.MOD_ID, id));
    }
}
