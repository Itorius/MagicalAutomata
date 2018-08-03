package com.thelastcog.magicalautomata.common.item;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.CustomEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nullable;

public abstract class EnergyProvider implements ICapabilityProvider
{

	//public final CustomEnergyStorage energyStorage;

   /* public EnergyProvider(final ItemStack stack, ItemPoweredScribingTools item)
    {
        this.energyStorage = new CustomEnergyStorage(item.maxPower, item.transfer, item.transfer)
        {
            @Override
            public int getEnergyStored()
            {
                if (stack.hasTagCompound())
                {
                    return stack.getTagCompound().getInteger("Energy");
                }
                else { return setEnergyStored(0).getEnergyStored(); }
            }

            @Override
            public CustomEnergyStorage setEnergyStored(int energyAmount)
            {
                if (!stack.hasTagCompound())
                {
                    MagicalAutomata.logger.info("REEEE");
                    stack.setTagCompound(new NBTTagCompound());
                }

                stack.getTagCompound().setInteger("Energy", energyAmount);
                return super.setEnergyStored(energyAmount);
            }
        };
    }*/

   /* @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return this.getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityEnergy.ENERGY)
        {
            return CapabilityEnergy.ENERGY.cast(this.energyStorage);
        }
        return null;
    }*/
}
