package com.thelastcog.magicalautomata.common.item;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

import thaumcraft.api.items.IScribeTools;

public class ItemPoweredScribingTools extends MAItem implements IScribeTools, ICapabilityProvider
{
	public CustomEnergyStorage energyStorage = new CustomEnergyStorage(10000, 100)
	{

	};

	public ItemPoweredScribingTools()
	{
		super("powered_scribing_tool");
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

	@Override
	public int getDamage(ItemStack stack)
	{
		return super.getDamage(stack);
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		super.setDamage(stack, damage);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored() + "RF");
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public boolean isDamageable()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	//<editor-fold desc="Deprecated">
	/*public void setEnergy(ItemStack stack, int energyAmount)
	{
		MagicalAutomata.logger.info("-2");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof CustomEnergyStorage)
			{
				((CustomEnergyStorage) storage).setEnergyStored(energyAmount);
			}
		}
	}

	public void receiveEnergyInternal(ItemStack stack, int maxReceive, boolean simulate)
	{
		MagicalAutomata.logger.info("-1");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof CustomEnergyStorage)
			{
				((CustomEnergyStorage) storage).receiveEnergyInternal(maxReceive, simulate);
			}
		}
	}

	public void extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate)
	{
		MagicalAutomata.logger.info("0");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof CustomEnergyStorage)
			{
				((CustomEnergyStorage) storage).extractEnergyInternal(maxExtract, simulate);
			}
		}
	}

	public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate)
	{
		MagicalAutomata.logger.info("1");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null)
			{
				return storage.receiveEnergy(maxReceive, simulate);
			}
		}
		return 0;
	}

	public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate)
	{
		MagicalAutomata.logger.info("2");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null)
			{
				return storage.extractEnergy(maxExtract, simulate);
			}
		}
		return 0;
	}

	public int getEnergyStored(ItemStack stack)
	{
		MagicalAutomata.logger.info("3");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null)
			{
				MagicalAutomata.logger.info("3A");
				return storage.getEnergyStored();
			}
		}
		return 0;
	}

	public int getMaxEnergyStored(ItemStack stack)
	{
		MagicalAutomata.logger.info("4");
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
		{
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage != null)
			{
				return storage.getMaxEnergyStored();
			}
		}
		return 0;
	}*/
	//</editor-fold>

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
	{
		return this;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		return false;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T)energyStorage;
		return null;
	}
}
