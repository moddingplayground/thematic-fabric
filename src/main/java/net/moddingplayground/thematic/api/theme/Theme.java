package net.moddingplayground.thematic.api.theme;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.thematic.Thematic;
import net.moddingplayground.thematic.block.ThematicLadderBlock;
import net.moddingplayground.thematic.block.themed.MechanicalLanternBlock;
import net.moddingplayground.thematic.block.themed.RusticLanternBlock;
import net.moddingplayground.thematic.block.themed.SunkenLanternBlock;
import net.moddingplayground.thematic.item.ThemeItem;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.moddingplayground.thematic.api.theme.DefaultDecoratables.*;

public enum Theme {
    RUSTIC("ancient_rope", () -> 0x8B6C52, false, Map.of(
        LANTERN, d -> new Decoratable(d, t -> new RusticLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN))),
        LADDER, d -> new Decoratable(d, (theme, decoratable, block) -> {
            FuelRegistry fuel = FuelRegistry.INSTANCE;
            fuel.add(block, 300);
        })
    )),
    SUNKEN("overgrown_anchor", () -> 0x575F71, true, Map.of(
        LANTERN, d -> new Decoratable(d, t -> new SunkenLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN))),
        LADDER, d -> new Decoratable(d, t -> new ThematicLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER)
                                                                                        .sounds(BlockSoundGroup.CHAIN)
                                                                                        .strength(3.5f).requiresTool()))
    )),
    MECHANICAL("oxidized_cog", () -> 0x4C9484, true, Map.of(
        LANTERN, d -> new Decoratable(d, t -> new MechanicalLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN))),
        LADDER, d -> new Decoratable(d, t -> new ThematicLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER)
                                                                                        .sounds(BlockSoundGroup.COPPER)
                                                                                        .strength(3.5f).requiresTool()))
    ));

    private static final Theme[] THEMES = Theme.values();

    private final Supplier<Integer> tooltipColor;
    private final boolean metallic;
    private final Map<Decoratable, Function<Decoratable, Decoratable>> overrides;

    private final String id, translationKey;
    private final Item item;
    private final ItemGroup itemGroup;

    Theme(String item, Supplier<Integer> tooltipColor, boolean metallic, Map<Decoratable, Function<Decoratable, Decoratable>> overrides) {
        this.tooltipColor = tooltipColor;
        this.metallic = metallic;
        this.overrides = overrides;

        this.id = this.name().toLowerCase();
        this.translationKey = "%s.theme.%s".formatted(Thematic.MOD_ID, this.id);

        this.item = Registry.register(
            Registry.ITEM, new Identifier(Thematic.MOD_ID, item),
            new ThemeItem(this, new FabricItemSettings().maxCount(1).group(Thematic.ITEM_GROUP))
        );
        this.itemGroup = FabricItemGroupBuilder.build(
            new Identifier(Thematic.MOD_ID, "theme_%s".formatted(this.getId())),
            () -> new ItemStack(this.getItem())
        );
    }

    public String getId() {
        return this.id;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public Item getItem() {
        return this.item;
    }

    public int getTooltipColor() {
        return this.tooltipColor.get();
    }

    public boolean isMetallic() {
        return this.metallic;
    }

    public ItemGroup getItemGroup() {
        return this.itemGroup;
    }

    public Decoratable override(Decoratable decoratable) {
        return Optional.ofNullable(this.overrides.get(decoratable)).orElse(d -> decoratable).apply(decoratable);
    }

    public Block get(Decoratable decoratable) {
        return Registry.BLOCK.get(Thematic.defaultedId(decoratable.format(this)));
    }

    public static void forEach(Consumer<Theme> action) {
        for (Theme theme : THEMES) action.accept(theme);
    }
}
