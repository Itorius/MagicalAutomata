package com.thelastcog.magicalautomata.common.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import com.thelastcog.magicalautomata.common.tileentity.TileEntityCreativeEnergySource;
import com.thelastcog.magicalautomata.compat.TOPCompatibility;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;

public class BlockCreativeEnergySource extends MABlock implements ITileEntityProvider, TOPCompatibility.TOPInfoProvider
{
	public BlockCreativeEnergySource()
	{
		super("creative_energy_source", Material.ROCK);
	}

	@Nullable @Override public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCreativeEnergySource();
	}

	@Override
	public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
	{
		TileEntity te = world.getTileEntity(data.getPos());

		if (te instanceof TileEntityCreativeEnergySource)
		{
			TileEntityCreativeEnergySource creativeEnergySourceTE = (TileEntityCreativeEnergySource)te;

			probeInfo.horizontal().text(TextFormatting.BLUE + "Currently providing: " + creativeEnergySourceTE.getLastProvided());
		}
	}
}
