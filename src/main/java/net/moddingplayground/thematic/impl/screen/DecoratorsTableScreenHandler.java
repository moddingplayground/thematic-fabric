package net.moddingplayground.thematic.impl.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.moddingplayground.thematic.api.item.ThemeItem;
import net.moddingplayground.thematic.impl.block.ThematicBlocks;
import net.moddingplayground.thematic.impl.recipe.ThematicRecipeType;

public class DecoratorsTableScreenHandler extends AbstractRecipeScreenHandler<CraftingInventory> {
    public final ScreenHandlerContext context;
    public final PlayerEntity player;
    public final Slot inputSlot, themeSlot, resultSlot;

    public final CraftingInventory input = new CraftingInventory(this, 1, 2);
    public final CraftingResultInventory result = new CraftingResultInventory();

    public final int startTable = 0;
    public final int endTable = 2;
    public final int startInventory = 3;
    public final int endInventory = 30;
    public final int startHotbar = 30;
    public final int endHotbar = 39;

    public DecoratorsTableScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public DecoratorsTableScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ThematicScreenHandlerType.DECORATORS_TABLE, syncId);
        this.context = context;
        this.player = inventory.player;

        // table
        this.inputSlot = this.addSlot(new IngredientSlot(this.input, 0, 26, 18));
        this.themeSlot = this.addSlot(new ThemedItemSlot(this.input, 1, 26, 52));
        this.resultSlot = this.addSlot(new ResultSlot(inventory.player, this.input, this.result, 2, 134, 35));

        // inventory
        for (int h = 0; h < 3; h++) for (int w = 0; w < 9; w++) this.addSlot(new Slot(inventory, w + h * 9 + 9, 8 + w * 18, 84 + h * 18));
        // hotbar
        for (int w = 0; w < 9; w++) this.addSlot(new Slot(inventory, w, 8 + w * 18, 142));
    }

    public void updateResult(World world, BlockPos pos) {
        if (this.player instanceof ServerPlayerEntity splayer) {
            ItemStack result = world.getServer().getRecipeManager().getFirstMatch(ThematicRecipeType.THEMING, this.input, world)
                                    .map(recipe -> this.result.shouldCraftRecipe(world, splayer, recipe) ? recipe.craft(this.input) : null)
                                    .orElse(ItemStack.EMPTY);
            this.result.setStack(this.resultSlot.id, result);
            this.setPreviousTrackedSlot(this.resultSlot.id, result);
            splayer.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(this.syncId, this.nextRevision(), this.resultSlot.id, result));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run(this::updateResult);
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(Recipe<? super CraftingInventory> recipe) {
        return recipe.matches(this.input, this.player.world);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return DecoratorsTableScreenHandler.canUse(this.context, player, ThematicBlocks.DECORATORS_TABLE);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasStack()) {
            ItemStack stack = slot.getStack();
            ret = stack.copy();

            ItemStack ingredient = this.inputSlot.getStack();
            ItemStack theme = this.themeSlot.getStack();

            if (slot == this.resultSlot) {
                if (!this.insertItem(stack, this.startInventory, this.endHotbar, true)) return ItemStack.EMPTY;
                slot.onQuickTransfer(stack, ret);
            } else if (slot == this.inputSlot || slot == this.themeSlot
                ? !this.insertItem(stack, this.startInventory, this.endHotbar, false)
                : (ingredient.isEmpty() || theme.isEmpty()
                    ? !this.insertItem(stack, this.startTable, this.endTable, false)
                    : (index >= 3 && index < 30
                        ? !this.insertItem(stack, this.startHotbar, this.endHotbar, false)
                        : index >= this.startHotbar && index < this.endHotbar && !this.insertItem(stack, this.startInventory, this.endInventory, false)))) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();

            if (stack.getCount() == ret.getCount()) return ItemStack.EMPTY;
            slot.onTakeItem(player, stack);
        }

        return ret;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return this.resultSlot.id;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return this.endTable + 1;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }

    public static class IngredientSlot extends Slot {
        public IngredientSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return !(stack.getItem() instanceof ThemeItem);
        }
    }

    public static class ThemedItemSlot extends Slot {
        public ThemedItemSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof ThemeItem;
        }
    }

    public static class ResultSlot extends CraftingResultSlot {
        private final PlayerEntity player;
        private final CraftingInventory input;

        public ResultSlot(PlayerEntity player, CraftingInventory input, Inventory inventory, int index, int x, int y) {
            super(player, input, inventory, index, x, y);
            this.player = player;
            this.input = input;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            this.onCrafted(stack);
            DefaultedList<ItemStack> stacks = player.world.getRecipeManager().getRemainingStacks(ThematicRecipeType.THEMING, this.input, player.world);
            for (int i = 0; i < stacks.size(); ++i) {
                ItemStack input = this.input.getStack(i);
                ItemStack result = stacks.get(i);
                if (!input.isEmpty()) {
                    this.input.removeStack(i, 1);
                    input = this.input.getStack(i);
                }
                if (result.isEmpty()) continue;
                if (input.isEmpty()) {
                    this.input.setStack(i, result);
                    continue;
                }
                if (ItemStack.areItemsEqualIgnoreDamage(input, result) && ItemStack.areNbtEqual(input, result)) {
                    result.increment(input.getCount());
                    this.input.setStack(i, result);
                    continue;
                }
                if (this.player.getInventory().insertStack(result)) continue;
                this.player.dropItem(result, false);
            }
        }
    }
}
