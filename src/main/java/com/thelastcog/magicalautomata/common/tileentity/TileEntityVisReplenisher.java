package com.thelastcog.magicalautomata.common.tileentity;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityVisReplenisher extends TileEntity implements ITickable
{
	@Override
	public void update()
	{
		World w = getWorld();
		w.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, new Random().nextDouble() - 0.5D, 1.0D,
				new Random().nextDouble() - 0.5D);
	}
}
