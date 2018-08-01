package com.thelastcog.magicalautomata.common.container;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotValidated extends SlotItemHandler
{
	IItemHandler handler;

	public SlotValidated(IItemHandler handler, int index, int xPosition, int yPosition)
	{
		super(handler, index, xPosition, yPosition);
		this.handler = handler;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return handler.isItemValid(getSlotIndex(), stack);
	}
}