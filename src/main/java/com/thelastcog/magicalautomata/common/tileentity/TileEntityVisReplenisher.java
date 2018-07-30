package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

import thaumcraft.api.aura.AuraHelper;

public class TileEntityVisReplenisher extends TileEntity implements ITickable, ICapabilityProvider
{
	private int timer = 60;

	public CustomEnergyStorage energyStorage = new CustomEnergyStorage(1000000, 5000);

	@Override
	public void update()
	{
		if (world != null && !world.isRemote)
		{
			if (--timer == 0)
			{
				timer = 60;

				if (energyStorage.getEnergyStored() >= 1000)
				{
					AuraHelper.addVis(getWorld(), pos, 1);
					energyStorage.extractEnergy(1000, false);
				}
			}
		}
	}

	@Nullable @Override public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;

		return super.getCapability(capability, facing);
	}

	@Override public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@Override public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		energyStorage.writeToNBT(compound);
		return super.writeToNBT(compound);
	}

	@Override public void readFromNBT(NBTTagCompound compound)
	{
		energyStorage.readFromNBT(compound);
		super.readFromNBT(compound);
	}
}
