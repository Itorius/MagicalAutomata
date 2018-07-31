package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

public class TileEntityPoweredEssentiaSmelter extends TileEntity implements ITickable, ICapabilityProvider
{
	public int vis;

	private CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 500);

	private ItemStackHandler itemStackHandler = new ItemStackHandler(1)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			//TestContainerTileEntity.this.markDirty();
		}
	};

	@Override public void update()
	{

	}

	@Override public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@Nullable @Override public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);

		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("energy"))
			energyStorage.readFromNBT(compound.getCompoundTag("energy"));

		if (compound.hasKey("item"))
			itemStackHandler.deserializeNBT(compound.getCompoundTag("item"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagCompound compoundEnergy = new NBTTagCompound();
		energyStorage.writeToNBT(compoundEnergy);
		compound.setTag("energy", compoundEnergy);

		compound.setTag("item", itemStackHandler.serializeNBT());

		return compound;
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}
}
