package com.thelastcog.magicalautomata.common.blocks;

import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;
import com.thelastcog.magicalautomata.compat.TOPCompatibility;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFluxScrubber extends MABlock implements ITileEntityProvider, TOPCompatibility.TOPInfoProvider
{
     public BlockFluxScrubber() { super("flux_scrubber", Material.ROCK, MapColor.STONE); }

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

               probeInfo.horizontal().text(TextFormatting.LIGHT_PURPLE + Integer.toString(seconds) + " seconds until next Flux scrub");
          }
     }
}
