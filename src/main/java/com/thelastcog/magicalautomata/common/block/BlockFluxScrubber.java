package com.thelastcog.magicalautomata.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;

public class BlockFluxScrubber extends MABlock implements ITileEntityProvider
{
	public BlockFluxScrubber()
	{
		super("flux_scrubber", Material.IRON, MapColor.STONE);
		setSoundType(SoundType.METAL);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFluxScrubber();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
			return true;

		TileEntity te = world.getTileEntity(pos);

		if (!(te instanceof TileEntityFluxScrubber))
			return false;

		player.openGui(MagicalAutomata.instance, 2, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
