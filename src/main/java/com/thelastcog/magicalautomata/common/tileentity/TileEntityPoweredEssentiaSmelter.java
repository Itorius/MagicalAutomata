package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sun.istack.internal.NotNull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class TileEntityPoweredEssentiaSmelter extends TileEntity implements ITickable, ICapabilityProvider {
    public int vis;

    private CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 500);

    private ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityPoweredEssentiaSmelter.this.markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            AspectList al;
            return (al = ThaumcraftCraftingManager.getObjectTags(stack)) != null && al.size() > 0;
        }

        @Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (stack.isEmpty())
                return ItemStack.EMPTY;

            validateSlotIndex(slot);

            if (!isItemValid(slot, stack)) return stack;

            ItemStack existing = this.stacks.get(slot);

            int limit = getStackLimit(slot, stack);

            if (!existing.isEmpty()) {
                if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                    return stack;

                limit -= existing.getCount();
            }

            if (limit <= 0)
                return stack;

            boolean reachedLimit = stack.getCount() > limit;

            if (!simulate) {
                if (existing.isEmpty()) {
                    this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
                } else {
                    existing.grow(reachedLimit ? limit : stack.getCount());
                }
                onContentsChanged(slot);
            }

            return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
        }
    };

    @Override
    public void update() {

    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return (T) energyStorage;
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);

        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("energy"))
            energyStorage.readFromNBT(compound.getCompoundTag("energy"));

        if (compound.hasKey("item"))
            itemStackHandler.deserializeNBT(compound.getCompoundTag("item"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagCompound compoundEnergy = new NBTTagCompound();
        energyStorage.writeToNBT(compoundEnergy);
        compound.setTag("energy", compoundEnergy);

        compound.setTag("item", itemStackHandler.serializeNBT());

        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
}
