package net.moddingplayground.thematic.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.Thematic;

public class ThematicItems {
    public static final Item ANCIENT_ROPE = unstackable("ancient_rope");
    public static final Item OVERGROWN_ANCHOR = unstackable("overgrown_anchor");
    public static final Item OXIDIZED_COG = unstackable("oxidized_cog");

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Thematic.MOD_ID, id), item);
    }

    private static Item unstackable(String id, ItemFactory<Item> factory) {
        return register(id, factory.create(new FabricItemSettings().maxCount(1).group(Thematic.ITEM_GROUP)));
    }

    private static Item unstackable(String id) {
        return unstackable(id, Item::new);
    }

    @FunctionalInterface
    private interface ItemFactory<T extends Item> { T create(FabricItemSettings settings); }
}
