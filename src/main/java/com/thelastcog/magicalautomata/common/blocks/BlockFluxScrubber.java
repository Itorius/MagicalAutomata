package com.thelastcog.magicalautomata.common.blocks;

import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFluxScrubber extends MABlock implements ITileEntityProvider
{
     public BlockFluxScrubber() { super("flux_scrubber", Material.ROCK, MapColor.STONE); }

     @Nullable
     @Override
     public TileEntity createNewTileEntity(World worldIn, int meta)
     {
         return new TileEntityFluxScrubber();
     }
}
