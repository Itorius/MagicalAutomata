package com.thelastcog.magicalautomata.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

import thaumcraft.api.items.IScribeTools;

public class ItemPoweredScribingTools extends MAItem implements IScribeTools
{
	public ItemPoweredScribingTools()
	{
		super("powered_scribing_tools");
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

	@Override public int getMaxDamage(ItemStack stack)
	{
		IEnergyStorage handler = stack.getCapability(EnergyProvider.ENERGY, null);
		return handler.getMaxEnergyStored() / 100;
	}

	@Override
	public int getDamage(ItemStack stack)
	{
		IEnergyStorage handler = stack.getCapability(EnergyProvider.ENERGY, null);
		return (int)Math.ceil((handler.getMaxEnergyStored() - handler.getEnergyStored()) / 100f);
	}

	@Override
	public void setDamage(ItemStack stack, int damage)
	{
		IEnergyStorage handler = stack.getCapability(EnergyProvider.ENERGY, null);
		handler.extractEnergy(100 * (damage - getDamage(stack)), false);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, tooltip, flagIn);

		IEnergyStorage handler = stack.getCapability(EnergyProvider.ENERGY, null);
		tooltip.add(handler.getEnergyStored() + "/" + handler.getMaxEnergyStored() + "RF");
	}

	//<editor-fold desc="Overrides">
	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Nullable @Override public NBTTagCompound getNBTShareTag(ItemStack stack)
	{
		NBTTagCompound compound = new NBTTagCompound();
		CustomEnergyStorage handler = (CustomEnergyStorage)stack.getCapability(EnergyProvider.ENERGY, null);
		handler.writeToNBT(compound);
		return compound;
	}

	@Override public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt)
	{
		CustomEnergyStorage handler = (CustomEnergyStorage)stack.getCapability(EnergyProvider.ENERGY, null);
		handler.readFromNBT(nbt);
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

	//</editor-fold>

	@Nullable @Override public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
	{
		return new EnergyProvider(stack);
	}
}
