package com.thelastcog.magicalautomata.common.tileentity;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.CustomEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import thaumcraft.api.aura.AuraHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityFluxScrubber extends TileEntity implements ITickable, ICapabilityProvider
{
    private int timer = 2000;
    public CustomEnergyStorage energyStorage = new CustomEnergyStorage(1000000, 5000);

    @Override
    public void update() {
        if (world != null && !world.isRemote)
        {
            if (--timer == 0)
            {
                timer = 2000;

                if (energyStorage.getEnergyStored() >= 1000)
                {
                    AuraHelper.drainFlux(getWorld(), pos, 1, false);
                    energyStorage.extractEnergy(1000, false);
                    markDirty();
                }
            }
        }
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
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
