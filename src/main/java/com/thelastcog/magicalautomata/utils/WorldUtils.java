package com.thelastcog.magicalautomata.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtils
{
	public static EntityItem spawnItemStack(World worldIn, BlockPos pos, ItemStack stackIn)
	{
		return spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stackIn);
	}

	public static EntityItem spawnItemStack(World worldIn, double x, double y, double z, ItemStack stackIn)
	{
		EntityItem entityItem = new EntityItem(worldIn, x, y, z, stackIn);
		entityItem.motionX = 0;
		entityItem.motionZ = 0;
		if (!worldIn.isRemote)
			worldIn.spawnEntity(entityItem);
		return entityItem;
	}
}
