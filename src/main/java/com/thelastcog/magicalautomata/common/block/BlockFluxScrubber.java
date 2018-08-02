package com.thelastcog.magicalautomata.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;
import com.thelastcog.magicalautomata.compat.TOPCompatibility;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import thaumcraft.api.aura.AuraHelper;

public class BlockFluxScrubber extends MABlock implements ITileEntityProvider, TOPCompatibility.TOPInfoProvider
{
	public BlockFluxScrubber()
	{
		super("flux_scrubber", Material.ROCK, MapColor.STONE);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityFluxScrubber();
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
	{
		TileEntity te = world.getTileEntity(data.getPos());

		if (te instanceof TileEntityFluxScrubber)
		{
			TileEntityFluxScrubber fluxScrubberTE = (TileEntityFluxScrubber)te;
			int seconds = fluxScrubberTE.getTimer() / 20;

			probeInfo.horizontal().text(TextFormatting.DARK_PURPLE + "Current Flux: " + Float.toString(AuraHelper.getFlux(world, data.getPos())));
			probeInfo.horizontal().text(TextFormatting.LIGHT_PURPLE + Integer.toString(seconds) + " seconds until next Flux scrub");
		}
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
