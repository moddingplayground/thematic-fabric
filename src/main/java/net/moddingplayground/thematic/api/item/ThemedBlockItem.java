package net.moddingplayground.thematic.api.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.theme.Theme;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThemedBlockItem extends BlockItem implements Themed {
    private final Theme theme;

    public ThemedBlockItem(Theme theme, Block block, Settings settings) {
        super(block, settings);
        this.theme = theme;
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext ctx) {
        this.addColoredTooltip(tooltip);
        super.appendTooltip(stack, world, tooltip, ctx);
    }
}
