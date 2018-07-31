package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;
import com.thelastcog.magicalautomata.common.blocks.ModBlocks;

import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class TileEntityPoweredEssentiaSmelter extends TileEntity implements ITickable, ICapabilityProvider, IInventory
{
	public int vis;

	private CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 500);

	private ItemStack[] itemArr = new ItemStack[1];

	public TileEntityPoweredEssentiaSmelter()
	{
		itemArr[0] = ItemStack.EMPTY;
	}

	@Override public void update()
	{

	}

	@Override public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		return super.hasCapability(capability, facing);
	}

	@Nullable @Override public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;
		return super.getCapability(capability, facing);
	}

	@Override public int getSizeInventory()
	{
		return itemArr.length;
	}

	@Override public boolean isEmpty()
	{
		for (ItemStack itemstack : itemArr)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override public ItemStack getStackInSlot(int index)
	{
		return itemArr[index];
	}

	@Override public ItemStack decrStackSize(int index, int count)
	{
		if (itemArr[index] != null)
		{
			ItemStack itemStack;
			if (itemArr[index].getCount() <= count)
			{
				itemStack = itemArr[index];
				setInventorySlotContents(index, null);
				markDirty();
				return itemStack;
			}
			else
			{
				itemStack = itemArr[index].splitStack(count);

				if (itemArr[index].getCount() == 0)
					setInventorySlotContents(index, null);

				markDirty();
				return itemStack;
			}
		}

		return null;
	}

	@Override public ItemStack removeStackFromSlot(int index)
	{
		return null;
	}

	@Override public void setInventorySlotContents(int index, ItemStack stack)
	{
		itemArr[index] = stack;
		if (stack.getCount() > getInventoryStackLimit())
			stack.setCount(getInventoryStackLimit());
		markDirty();
	}

	@Override public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override public boolean isUsableByPlayer(EntityPlayer player)
	{
		return world.getTileEntity(pos) == this && getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() * 0.5F) <= 64D;
	}

	@Override public void openInventory(EntityPlayer player)
	{

	}

	@Override public void closeInventory(EntityPlayer player)
	{

	}

	@Override public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		AspectList al;
		return (al = ThaumcraftCraftingManager.getObjectTags(stack)) != null && al.size() > 0;
	}

	@Override public int getField(int id)
	{
		return 0;
	}

	@Override public void setField(int id, int value)
	{

	}

	@Override public int getFieldCount()
	{
		return 0;
	}

	@Override public void clear()
	{

	}

	@Override public String getName()
	{
		return ModBlocks.powered_essentia_smelter.getUnlocalizedName();
	}

	@Override public boolean hasCustomName()
	{
		return false;
	}
}
