package com.thelastcog.magicalautomata.common.item;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.thelastcog.magicalautomata.MAInfo;

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