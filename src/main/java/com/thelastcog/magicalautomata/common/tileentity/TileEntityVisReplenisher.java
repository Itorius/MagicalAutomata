package com.thelastcog.magicalautomata.common.tileentity;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;

public class TileEntityVisReplenisher extends TileEntity implements ITickable
{
	@Override
	public void update()
	{
		WorldClient w = (WorldClient)getWorld();
		w.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 0.0D, 1.0D, 0.0D, new int[0]);
	}
}
