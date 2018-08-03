package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

import thaumcraft.api.aura.AuraHelper;

public class TileEntityVisReplenisher extends TileEntity implements ITickable, ICapabilityProvider
{
	private int timer = 100;

	private CustomEnergyStorage energyStorage = new CustomEnergyStorage(1000000000, 25000000)
	{
		@Override public int receiveEnergy(int maxReceive, boolean simulate)
		{
			markDirty();
			return super.receiveEnergy(maxReceive, simulate);
		}
	};
	private ItemStackHandler itemStackHandler = new ItemStackHandler(1)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			TileEntityVisReplenisher.this.markDirty();
		}

		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			return stack.hasCapability(CapabilityEnergy.ENERGY, null) && stack.getCapability(CapabilityEnergy.ENERGY, null).canExtract();
		}

		@Override
		@Nonnull
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
		{
			if (stack.isEmpty())
				return ItemStack.EMPTY;

			validateSlotIndex(slot);

			if (!isItemValid(slot, stack))
				return stack;

			ItemStack existing = stacks.get(slot);

			int limit = getStackLimit(slot, stack);

			if (!existing.isEmpty())
			{
				if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
					return stack;

				limit -= existing.getCount();
			}

			if (limit <= 0)
				return stack;

			boolean reachedLimit = stack.getCount() > limit;

			if (!simulate)
			{
				if (existing.isEmpty())
				{
					stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
				}
				else
				{
					existing.grow(reachedLimit ? limit : stack.getCount());
				}
				onContentsChanged(slot);
			}

			return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
		}
	};

	@Override
	public void update()
	{
		if (world != null && !world.isRemote)
		{
			ItemStack itemStack = itemStackHandler.getStackInSlot(0);
			if (!itemStack.isEmpty() && itemStack.hasCapability(CapabilityEnergy.ENERGY, null))
			{
				IEnergyStorage itemEnergy = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
				int energy = itemEnergy.extractEnergy(Math.min(energyStorage.getMaxEnergyStored() - energyStorage.getEnergyStored(), energyStorage.getMaxReceive()), false);
				energyStorage.receiveEnergy(energy, false);
			}

			if (--timer == 0)
			{
				timer = 100;

				int requiredEnergy = MathHelper.clamp((int)Math.pow(10, (AuraHelper.getVis(world, pos) / 100) * 2.5), 1000, 100000000);
				if (energyStorage.getEnergyStored() >= requiredEnergy)
				{
					AuraHelper.addVis(world, pos, 1);
					energyStorage.extractEnergy(requiredEnergy, false);

					markDirty();
				}
			}
		}
	}

	@Nullable @Override public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T)itemStackHandler;

		return super.getCapability(capability, facing);
	}

	@Override public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@Override public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		energyStorage.writeToNBT(compound);
		compound.setTag("item", itemStackHandler.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override public void readFromNBT(NBTTagCompound compound)
	{
		energyStorage.readFromNBT(compound);
		itemStackHandler.deserializeNBT(compound.getCompoundTag("item"));
		super.readFromNBT(compound);
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound compound = new NBTTagCompound();
		return writeToNBT(compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		NBTTagCompound compound = pkt.getNbtCompound();
		readFromNBT(compound);
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override public void markDirty()
	{
		super.markDirty();
		world.notifyBlockUpdate(pos, blockType.getBlockState().getBaseState(), blockType.getBlockState().getBaseState(), 0);
	}
}
