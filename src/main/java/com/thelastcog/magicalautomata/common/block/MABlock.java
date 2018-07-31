package com.thelastcog.magicalautomata.common.block;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

import com.thelastcog.magicalautomata.MagicalAutomata;

public abstract class MABlock extends Block
{
	public MABlock(String name, Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
		setBlockName(this, name);
		setCreativeTab(MagicalAutomata.instance.tabMagicalAutomata);
	}

	private static void setBlockName(final MABlock block, final String blockName)
	{
		block.setRegistryName(MagicalAutomata.MODID, blockName);
		final ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
		block.setUnlocalizedName(registryName.toString());
	}

	public MABlock(String name, Material materialIn)
	{
		this(name, materialIn, materialIn.getMaterialMapColor());
	}
}
