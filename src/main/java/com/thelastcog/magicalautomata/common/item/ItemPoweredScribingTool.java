package com.thelastcog.magicalautomata.common.item;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.CustomEnergyStorage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IScribeTools;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPoweredScribingTool extends MAItem implements IScribeTools
{
    public int maxPower;
    public int transfer;

    public ItemPoweredScribingTool(int maxPower, int transfer)
    {
        super("powered_scribing_tool");
        this.maxPower = maxPower;
        this.transfer = transfer;
        this.setMaxStackSize(1);
        this.setNoRepair();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(Integer.toString(getEnergyStored(stack)) + "/" + maxPower + " RF");
    }

    @Override
    public boolean getShareTag() { return true; }

    @Override
    public boolean isDamageable() { return true; }

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

    public void setEnergy(ItemStack stack, int energyAmount)
    {
        MagicalAutomata.logger.info("-2");
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null))
        {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof CustomEnergyStorage)
            {
                ((CustomEnergyStorage)storage).setEnergyStored(energyAmount);
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
                ((CustomEnergyStorage)storage).receiveEnergyInternal(maxReceive, simulate);
            }
        }
    }

    public void extractEnergyInternal(ItemStack stack, int maxExtract, boolean simulate)
    {
        MagicalAutomata.logger.info("0");
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null))
        {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage instanceof CustomEnergyStorage)
            {
                ((CustomEnergyStorage)storage).extractEnergyInternal(maxExtract, simulate);
            }
        }
    }

    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate)
    {
        MagicalAutomata.logger.info("1");
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null))
        {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null)
            {
                return storage.receiveEnergy(maxReceive, simulate);
            }
        }
        return 0;
    }

    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate)
    {
        MagicalAutomata.logger.info("2");
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null))
        {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null)
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
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new EnergyProvider(stack, this);
    }
}
