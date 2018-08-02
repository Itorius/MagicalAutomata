package com.thelastcog.magicalautomata.common.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import com.thelastcog.magicalautomata.common.CustomEnergyStorage;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.tiles.essentia.TileAlembic;

public class TileEntityPoweredEssentiaSmelter extends TileEntity implements ITickable, ICapabilityProvider
{
	private CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 500)
	{
		@Override
		public int receiveEnergy(int maxReceive, boolean simulate)
		{
			markDirty();
			return super.receiveEnergy(maxReceive, simulate);
		}
	};

	private ItemStackHandler itemStackHandler = new ItemStackHandler(2)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			TileEntityPoweredEssentiaSmelter.this.markDirty();
		}

		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack)
		{
			switch (slot)
			{
				case 0:
					AspectList al;
					return (al = ThaumcraftCraftingManager.getObjectTags(stack)) != null && al.size() > 0;
				case 1:
					return stack.hasCapability(CapabilityEnergy.ENERGY, null);
				default:
					return false;
			}
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
				} else
				{
					existing.grow(reachedLimit ? limit : stack.getCount());
				}
				onContentsChanged(slot);
			}

			return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
		}
	};

	public AspectList aspects = new AspectList();
	public int vis;
	public int smeltTime = 100;
	//public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;
	int timer = 0;
	public int capacity = 250;
	public int energyUsage = 100;
	//int bellows = -1;

	public float getEfficiency()
	{
		return 1.0f;
	}

	public int getSpeed()
	{
		return 10;
	}

	@Override
	public void update()
	{
		//boolean flag = furnaceBurnTime > 0;
		boolean shouldMarkDirty = false;
		++timer;
		/*if (furnaceBurnTime > 0)
		{
			--furnaceBurnTime;
		}*/

		if (world != null && !world.isRemote)
		{
            /*if (bellows < 0) {
                checkNeighbours();
            }*/
			int speed = getSpeed();

			if (timer % (speed / 2) == 0 && aspects.size() > 0)
			{
				for (Aspect aspect : aspects.getAspects())
				{
					if (aspects.getAmount(aspect) > 0 && insertToAlembic(getWorld(), getPos(), aspect))
					{
						takeFromContainer(aspect, 1);
						break;
					}
				}

				//<editor-fold desc="Auxilary attachments">
				/*block1:
				for (EnumFacing face : EnumFacing.HORIZONTALS)
				{
					IBlockState aux = world.getBlockState(getPos().offset(face));
					if (aux.getBlock() != BlocksTC.smelterAux || BlockStateUtils.getFacing(aux) != face.getOpposite())
						continue;
					for (Aspect aspect : aspects.getAspects())
					{
						if (aspects.getAmount(aspect) <= 0 || !insertToAlembic(getWorld(), getPos().offset(face), aspect))
							continue;
						takeFromContainer(aspect, 1);
						continue block1;
					}
				}*/
				//</editor-fold>
			}

			//<editor-fold desc="Fuel consuption">
			/*if (furnaceBurnTime == 0)
			{
				if (canSmelt())
				{
					currentItemBurnTime = furnaceBurnTime = TileEntityFurnace.getItemBurnTime(itemStackHandler.getStackInSlot(1));
					if (furnaceBurnTime > 0)
					{
						BlockSmelter.setFurnaceState(world, getPos(), true);
						shouldMarkDirty = true;

						if (itemStackHandler.getStackInSlot(1) != null)
						{
							itemStackHandler.getStackInSlot(1).shrink(1);
							if (itemStackHandler.getStackInSlot(1).getCount() == 0)
							{
								itemStackHandler.setStackInSlot(1, itemStackHandler.getStackInSlot(1).getItem().getContainerItem(itemStackHandler.getStackInSlot(1)));
							}
						}
					}
					else
					{
						BlockSmelter.setFurnaceState(world, getPos(), false);
					}
				}
				else
				{
					BlockSmelter.setFurnaceState(world, getPos(), false);
				}
			}*/
			//</editor-fold>

			if (/*BlockStateUtils.isEnabled(getBlockMetadata()) &&*/ canSmelt())
			{
				++furnaceCookTime;
				energyStorage.extractEnergy(energyUsage, false);
				shouldMarkDirty = true;
				//world.notifyBlockUpdate(pos, blockType.getDefaultState(), blockType.getDefaultState(), 0);
				if (furnaceCookTime >= smeltTime)
				{
					furnaceCookTime = 0;
					smeltItem();
					shouldMarkDirty = true;
				}
			} else
				furnaceCookTime = 0;

			/*if (flag != furnaceBurnTime > 0)
				shouldMarkDirty = true;*/
		}

		if (shouldMarkDirty)
			markDirty();
	}

	private boolean canSmelt()
	{
		if (energyStorage.getEnergyStored() < energyUsage)
			return false;

		if (itemStackHandler.getStackInSlot(0).isEmpty())
			return false;

		AspectList al = ThaumcraftCraftingManager.getObjectTags(itemStackHandler.getStackInSlot(0));
		if (al == null || al.size() == 0)
			return false;

		int vs = al.visSize();
		if (vs > capacity - vis)
			return false;
		smeltTime = vs * 2;
		markDirty();

		//world.notifyBlockUpdate(pos, blockType.getDefaultState(), blockType.getDefaultState(), 0);
		return true;
	}

	public boolean takeFromContainer(Aspect tag, int amount)
	{
		if (aspects != null && aspects.getAmount(tag) >= amount)
		{
			aspects.remove(tag, amount);
			this.vis = aspects.visSize();
			markDirty();
			//sendChangesToNearby();
			return true;
		}
		return false;
	}

	@Override
	public void markDirty()
	{
		super.markDirty();
		world.notifyBlockUpdate(pos, blockType.getBlockState().getBaseState(), blockType.getBlockState().getBaseState(), 0);
	}

	public void smeltItem()
	{
		if (canSmelt())
		{
			int flux = 0;
			AspectList al = ThaumcraftCraftingManager.getObjectTags(itemStackHandler.getStackInSlot(0));
			for (Aspect a : al.getAspects())
			{
				if (getEfficiency() < 1.0f)
				{
					int qq = al.getAmount(a);
					for (int q = 0; q < qq; ++q)
					{
						if (world.rand.nextFloat() <= (a == Aspect.FLUX ? getEfficiency() * 0.66f : getEfficiency()))
							continue;
						al.reduce(a, 1);
						++flux;
					}
				} else if (getEfficiency() > 1.0F) // Gain extra vis?
				{
					int qq = al.getAmount(a);
					for (int q = 0; q < qq; ++q)
					{
						if (world.rand.nextFloat() + 1 >= (a == Aspect.FLUX ? getEfficiency() * 0.66f : getEfficiency()))
							continue;
						al.add(a, 1);
					}
				}
				aspects.add(a, al.getAmount(a));
			}
			if (flux > 0)
			{
				int pp = 0;
				block2:
				for (int c = 0; c < flux; ++c)
				{
					for (EnumFacing face : EnumFacing.HORIZONTALS)
					{
						IBlockState vent = world.getBlockState(getPos().offset(face));
						if (vent.getBlock() != BlocksTC.smelterVent || BlockStateUtils.getFacing(vent) != face.getOpposite() || world.rand.nextFloat() >= 0.333)
							continue;
						world.addBlockEvent(getPos(), getBlockType(), 1, face.getOpposite().ordinal());
						continue block2;
					}
					++pp;
				}
				AuraHelper.polluteAura(getWorld(), getPos(), pp, true);
			}
			this.vis = aspects.visSize();

			itemStackHandler.getStackInSlot(0).shrink(1);
			if (itemStackHandler.getStackInSlot(0).getCount() <= 0)
				itemStackHandler.setStackInSlot(0, ItemStack.EMPTY);
		}
	}

	public static boolean insertToAlembic(World world, BlockPos pos, Aspect aspect)
	{
		TileEntity te;
		TileAlembic alembic;
		int deep = 1;
		while ((te = world.getTileEntity(pos.up(deep))) != null && te instanceof TileAlembic)
		{
			alembic = (TileAlembic) te;
			if (alembic.amount > 0 && alembic.aspect == aspect && alembic.addToContainer(aspect, 1) == 0)
			{
				return true;
			}
			++deep;
		}
		deep = 1;
		while ((te = world.getTileEntity(pos.up(deep))) != null && te instanceof TileAlembic)
		{
			alembic = (TileAlembic) te;
			if ((alembic.aspectFilter == null || alembic.aspectFilter == aspect) && alembic.addToContainer(aspect, 1) == 0)
			{
				return true;
			}
			++deep;
		}
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	public float getVisScaled(int height)
	{
		if (this.capacity == 0)
			return 0;
		return (this.vis / (float) this.capacity) * height;
	}

	@SideOnly(value = Side.CLIENT)
	public float getCookProgressScaled(int height)
	{
		if (this.smeltTime <= 0)
			this.smeltTime = 1;
		return (this.furnaceCookTime / (float) this.smeltTime) * height;
	}

	@SideOnly(value = Side.CLIENT)
	public float getEnergyScaled(int height)
	{
		return (energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored()) * height;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityEnergy.ENERGY)
			return (T) energyStorage;
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);

		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		energyStorage.readFromNBT(compound.getCompoundTag("energy"));
		itemStackHandler.deserializeNBT(compound.getCompoundTag("item"));

		vis = compound.getInteger("vis");
		furnaceCookTime = compound.getInteger("furnaceCookTime");
		smeltTime = compound.getInteger("smeltTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		NBTTagCompound compoundEnergy = new NBTTagCompound();
		energyStorage.writeToNBT(compoundEnergy);
		compound.setTag("energy", compoundEnergy);

		compound.setTag("item", itemStackHandler.serializeNBT());

		compound.setInteger("vis", vis);
		compound.setInteger("furnaceCookTime", furnaceCookTime);
		compound.setInteger("smeltTime", smeltTime);

		return compound;
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
}
