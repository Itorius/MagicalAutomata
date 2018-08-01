package com.thelastcog.magicalautomata.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;
import com.thelastcog.magicalautomata.utils.InventoryUtils;

import thaumcraft.api.aura.AuraHelper;

public class BlockPoweredEssentiaSmelter extends MABlock implements ITileEntityProvider
{
	public BlockPoweredEssentiaSmelter()
	{
		super("powered_essentia_smelter", Material.IRON);
		setSoundType(SoundType.METAL);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPoweredEssentiaSmelter();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityPoweredEssentiaSmelter tileentity = (TileEntityPoweredEssentiaSmelter)worldIn.getTileEntity(pos);
		if (tileentity != null && !worldIn.isRemote)
		{
			IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			InventoryUtils.drop(handler, worldIn, pos);

			if (tileentity.vis > 0)
				AuraHelper.polluteAura(worldIn, pos, tileentity.vis, true);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return true;

		TileEntity te = world.getTileEntity(pos);

		if (!(te instanceof TileEntityPoweredEssentiaSmelter))
			return false;

		player.openGui(MagicalAutomata.instance, 1, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
