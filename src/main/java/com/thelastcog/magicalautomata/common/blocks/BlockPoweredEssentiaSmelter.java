package com.thelastcog.magicalautomata.common.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;

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
		if (tileentity != null && !worldIn.isRemote && tileentity.vis > 0)
		{
			//tileentity.inv.drop(worldIn, pos);
			AuraHelper.polluteAura(worldIn, pos, tileentity.vis, true);
		}
		super.breakBlock(worldIn, pos, state);
	}
}
