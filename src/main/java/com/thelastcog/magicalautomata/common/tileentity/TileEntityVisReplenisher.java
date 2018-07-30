package com.thelastcog.magicalautomata.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import thaumcraft.api.aura.AuraHelper;

public class TileEntityVisReplenisher extends TileEntity implements ITickable
{
	private int timer = 60;

	@Override
	public void update()
	{
		if (--timer == 0)
		{
			timer = 60;

			AuraHelper.addVis(getWorld(), pos, 1);
		}
	}
}
