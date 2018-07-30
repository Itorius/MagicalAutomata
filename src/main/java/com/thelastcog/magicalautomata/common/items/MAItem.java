package com.thelastcog.magicalautomata.common.items;

import java.util.Objects;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.sun.istack.internal.NotNull;
import com.thelastcog.magicalautomata.MagicalAutomata;

public abstract class MAItem extends Item
{
	public MAItem(final String itemName)
	{
		setItemName(this, itemName);
		setCreativeTab(MagicalAutomata.instance.tabMagicalAutomata);
	}

	public static void setItemName(@NotNull MAItem item, String itemName)
	{
		item.setRegistryName(MagicalAutomata.MODID, itemName);
		final ResourceLocation regName = Objects.requireNonNull(item.getRegistryName());
		item.setUnlocalizedName(regName.toString());
	}
}