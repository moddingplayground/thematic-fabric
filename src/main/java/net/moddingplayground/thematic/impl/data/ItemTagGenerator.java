package net.moddingplayground.thematic.impl.data;

import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.thematic.api.Thematic;
import net.moddingplayground.thematic.api.tag.ThematicItemTags;
import net.moddingplayground.thematic.api.theme.Decoratable;
import net.moddingplayground.thematic.api.theme.Theme;
import net.moddingplayground.thematic.api.theme.data.DecoratableDataToymaker;

public class ItemTagGenerator extends AbstractTagGenerator<Item> {
    public ItemTagGenerator(String modId) {
        super(modId, Registry.ITEM);
    }

    @Override
    public void generate() {
        this.add(ThematicItemTags.RUSTIC_TREASURE_ITEMS,
            Items.DRAGON_EGG,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.END_CRYSTAL,
            Items.NETHER_STAR,
            Items.BEACON,
            Items.TOTEM_OF_UNDYING,
            Items.TRIDENT,
            Items.HEART_OF_THE_SEA,
            Items.ELYTRA,

            Items.NETHERITE_INGOT,
            Items.EMERALD,
            Items.DIAMOND,
            Items.GOLD_INGOT,
            Items.IRON_INGOT,

            Items.AMETHYST_SHARD,
            Items.NETHERITE_SCRAP,
            Items.GOLD_NUGGET,
            Items.IRON_NUGGET
        ).add(ItemTags.MUSIC_DISCS);

        for (Item item : Registry.ITEM) {
            if (item instanceof DyeItem) this.add(ThematicItemTags.DYES, item);
        }

        for (Theme theme : Thematic.THEME_REGISTRY) {
            for (Decoratable decoratable : Thematic.DECORATABLE_REGISTRY) {
                decoratable.getData(theme, DecoratableDataToymaker.class).ifPresent(t -> t.generateItemTags(this));
            }
        }
    }
}
