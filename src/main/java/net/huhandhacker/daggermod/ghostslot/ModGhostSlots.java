package net.huhandhacker.daggermod.ghostslot;

import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.SlotSelectTime;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class ModGhostSlots {
    private final Reference2ObjectMap<Slot, ModGhostSlots.GhostSlot> ingredients = new Reference2ObjectArrayMap<>();
    private final SlotSelectTime slotSelectTime;

    public ModGhostSlots(final SlotSelectTime slotSelectTime) {
        this.slotSelectTime = slotSelectTime;
    }

    public void clear() {
        this.ingredients.clear();
    }

    private void setSlot(final Slot slot, final ContextMap context, final SlotDisplay contents, final boolean isResult) {
        List<ItemStack> entries = contents.resolveForStacks(context);
        if (!entries.isEmpty()) {
            this.ingredients.put(slot, new ModGhostSlots.GhostSlot(entries, isResult));
        }
    }

    public void setInput(final Slot slot, final ContextMap context, final SlotDisplay contents) {
        this.setSlot(slot, context, contents, false);
    }

    public void setResult(final Slot slot, final ContextMap context, final SlotDisplay contents) {
        this.setSlot(slot, context, contents, true);
    }

    public void extractRenderState(final GuiGraphicsExtractor graphics, final Minecraft minecraft, final boolean isResultSlotBig) {
        this.ingredients.forEach((slot, ingredient) -> {
            int x = slot.x;
            int y = slot.y;
            if (ingredient.isResultSlot && isResultSlotBig) {
                graphics.fill(x - 4, y - 4, x + 20, y + 20, 822018048);
            } else {
                graphics.fill(x, y, x + 16, y + 16, 822018048);
            }

            ItemStack itemStack = ingredient.getItem(this.slotSelectTime.currentIndex());
            graphics.fakeItem(itemStack, x, y);
            graphics.fill(x, y, x + 16, y + 16, 822083583);
            if (ingredient.isResultSlot) {
                graphics.itemDecorations(minecraft.font, itemStack, x, y);
            }
        });
    }

    public void extractTooltip(
            final GuiGraphicsExtractor graphics, final Minecraft minecraft, final int mouseX, final int mouseY, final @Nullable Slot hoveredSlot
    ) {
        if (hoveredSlot != null) {
            ModGhostSlots.GhostSlot hoveredGhostSlot = this.ingredients.get(hoveredSlot);
            if (hoveredGhostSlot != null) {
                ItemStack hoveredItem = hoveredGhostSlot.getItem(this.slotSelectTime.currentIndex());
                graphics.setComponentTooltipForNextFrame(
                        minecraft.font, Screen.getTooltipFromItem(minecraft, hoveredItem), mouseX, mouseY, hoveredItem.get(DataComponents.TOOLTIP_STYLE)
                );
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private record GhostSlot(List<ItemStack> items, boolean isResultSlot) {
        public ItemStack getItem(final int itemIndex) {
            int size = this.items.size();
            return size == 0 ? ItemStack.EMPTY : this.items.get(itemIndex % size);
        }
    }
}
