package com.thelastcog.magicalautomata.common.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;

public class ContainerPoweredEssentiaSmeltery extends Container
{
	private TileEntityPoweredEssentiaSmelter te;

	public ContainerPoweredEssentiaSmeltery(IInventory playerInventory, TileEntityPoweredEssentiaSmelter te)
	{
		this.te = te;

		addOwnSlots();
		addPlayerSlots(playerInventory);
	}

	private void addPlayerSlots(IInventory playerInventory)
	{
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				int x = 10 + col * 18;
				int y = row * 18 + 70;
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
			}
		}

		for (int row = 0; row < 9; ++row)
		{
			int x = 10 + row * 18;
			int y = 58 + 70;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	private void addOwnSlots()
	{
		IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 10;
		int y = 6;

		// Add our own slots
		int slotIndex = 0;
		for (int i = 0; i < itemHandler.getSlots(); i++)
		{
			addSlotToContainer(new SlotValidated(itemHandler, slotIndex, x, y));
			slotIndex++;
			x += 18;
		}
	}

	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 1)
			{
				if (!this.mergeItemStack(itemstack1, 1, inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, 1, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override public boolean canInteractWith(EntityPlayer playerIn)
	{
		return te.canInteractWith(playerIn);
	}
}
