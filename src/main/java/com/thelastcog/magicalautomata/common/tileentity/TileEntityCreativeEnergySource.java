package com.thelastcog.magicalautomata.common.tileentity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import com.thelastcog.magicalautomata.MagicalAutomata;

public class TileEntityCreativeEnergySource extends TileEntity implements ITickable
{
	private long lastProvided;

	@Override public void update()
	{
		if (world != null && !world.isRemote)
		{
			lastProvided = giveEnergyAllFaces(world, pos, Integer.MAX_VALUE, false);
		}
	}

	public static long giveEnergyAllFaces(@Nonnull World world, BlockPos pos, int energy, boolean simulate)
	{
		HashMap<EnumFacing, TileEntity> tiles = new HashMap<>();
		for (EnumFacing side : EnumFacing.VALUES)
		{
			TileEntity te = world.getTileEntity(pos.offset(side));
			if (te == null)
				continue;
			if (te.hasCapability(CapabilityEnergy.ENERGY, side))
				tiles.put(side, te);
		}
		if (tiles.size() <= 0)
			return 0;

		Iterator<Entry<EnumFacing, TileEntity>> tilesIterator = tiles.entrySet().iterator();
		long energyGiven = 0;
		while (tilesIterator.hasNext())
		{
			Entry<EnumFacing, TileEntity> entry = tilesIterator.next();
			EnumFacing side = entry.getKey();
			TileEntity te = entry.getValue();
			energyGiven += te.getCapability(CapabilityEnergy.ENERGY, side).receiveEnergy(energy, simulate);
		}
		return energyGiven;
	}

	public float getLastProvided()
	{
		return lastProvided;
	}
}
