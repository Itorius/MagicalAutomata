package com.thelastcog.magicalautomata.common.item;

import java.util.HashSet;
import java.util.Set;

import com.thelastcog.magicalautomata.MAInfo;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(MAInfo.MODID)
public class ModItems
{
	public static final ItemPoweredScribingTools powered_scribing_tools = null;

	@Mod.EventBusSubscriber(modid = MAInfo.MODID)
	public static class RegistrationHandler
	{
		public static final Set<Item> ITEMS = new HashSet<>();

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			final Item[] items = {
					new ItemPoweredScribingTools()
			};

			final IForgeRegistry<Item> registry = event.getRegistry();
			for (final Item item : items)
			{
				registry.register(item);
				ITEMS.add(item);
			}
		}
	}
}