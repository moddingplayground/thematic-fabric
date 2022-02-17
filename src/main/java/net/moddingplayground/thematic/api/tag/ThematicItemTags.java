package net.moddingplayground.thematic.api.tag;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.moddingplayground.thematic.api.Thematic;

/**
 * Item tags provided by Thematic.
 */
public final class ThematicItemTags {
    public static final Tag.Identified<Item> DYES = register("dyes");
    public static final Tag.Identified<Item> RUSTIC_TREASURE_ITEMS = register("rustic_treasure_items");

    private ThematicItemTags() {}

    private static Tag.Identified<Item> register(String id) {
        return TagFactory.ITEM.create(new Identifier(Thematic.MOD_ID, id));
    }
}
