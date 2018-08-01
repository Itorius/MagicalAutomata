package com.thelastcog.magicalautomata.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryUtils
{
	public static void drop(IItemHandler inv, World world, BlockPos pos)
	{
		if (inv == null || world == null || pos == null)
			return;

		if (!world.isRemote)
		{
			for (int i = 0; i < inv.getSlots(); ++i)
			{
				ItemStack s = inv.getStackInSlot(i);
				if (s == null || s.isEmpty())
					continue;
				WorldUtils.spawnItemStack(world, pos, s);
			}
		}
	}
}
