package com.thelastcog.magicalautomata.common.item;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.IEnergyStorage;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

public class EnergyProvider implements ICapabilitySerializable<NBTTagCompound>
{
	@CapabilityInject(IEnergyStorage.class)
	public static Capability<IEnergyStorage> ENERGY = null;

	public final CustomEnergyStorage energyStorage = new CustomEnergyStorage(10000, 100)
	{
		@Override public int receiveEnergy(int maxReceive, boolean simulate)
		{
			return super.receiveEnergy(maxReceive, simulate);
		}
	};

	public ItemStack stack;

	public EnergyProvider(ItemStack stack)
	{
		this.stack = stack;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return getCapability(capability, facing) != null;
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == ENERGY)
			return (T)energyStorage;

		return null;
	}

	@Override public NBTTagCompound serializeNBT()
	{
		NBTTagCompound compound = new NBTTagCompound();
		energyStorage.writeToNBT(compound);
		return compound;
	}

	@Override public void deserializeNBT(NBTTagCompound nbt)
	{
		energyStorage.readFromNBT(nbt);
	}
}
