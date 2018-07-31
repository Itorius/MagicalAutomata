package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;
import com.thelastcog.magicalautomata.utils.MathUtils;

import thaumcraft.api.aura.AuraHelper;

public class TileEntityFluxScrubber extends TileEntity implements ITickable, ICapabilityProvider
{
	private int timer = 2000;
	private CustomEnergyStorage energyStorage = new CustomEnergyStorage(1000000000, 25000000);

	@Override
	public void update()
	{
		if (world != null && !world.isRemote)
		{
			if (--timer == 0)
			{
				timer = 2000;

				int requiredEnergy = MathUtils.clamp((int)Math.pow(10, AuraHelper.getFlux(world, pos) / 5), 1000, 100000000);

				if (energyStorage.getEnergyStored() >= requiredEnergy)
				{
					AuraHelper.drainFlux(world, pos, 1, false);
					energyStorage.extractEnergy(requiredEnergy, false);

					markDirty();
				}
			}
		}
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;

		return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		energyStorage.writeToNBT(compound);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		energyStorage.readFromNBT(compound);
		super.readFromNBT(compound);
	}

	public int getTimer()
	{
		return timer;
	}
}
