package com.thelastcog.magicalautomata.common.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.thelastcog.magicalautomata.common.tileentity.TileEntityVisReplenisher;

public class BlockVisReplenisher extends MABlock implements ITileEntityProvider
{
	public BlockVisReplenisher()
	{
		super("vis_replenisher", Material.ROCK, MapColor.STONE);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityVisReplenisher();
	}
}
