package com.thelastcog.magicalautomata.common.blocks;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.google.common.base.Preconditions;
import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityVisReplenisher;

@GameRegistry.ObjectHolder(MagicalAutomata.MODID)
public class ModBlocks
{
	public static final BlockVisReplenisher vis_replenisher = null;

	@Mod.EventBusSubscriber(modid = MagicalAutomata.MODID)
	public static class RegistrationHandler
	{
		public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event)
		{
			final IForgeRegistry<Block> registry = event.getRegistry();

			final Block[] blocks = {
					new BlockVisReplenisher()
			};
			registry.registerAll(blocks);
			registerTileEntities();
		}

		@SubscribeEvent
		public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
		{
			final ItemBlock[] items = {
					new ItemBlock(vis_replenisher)
			};

			final IForgeRegistry<Item> registry = event.getRegistry();

			for (final ItemBlock item : items)
			{
				final Block block = item.getBlock();
				final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
				registry.register(item.setRegistryName(registryName));
				ITEM_BLOCKS.add(item);
			}
		}

		private static void registerTileEntities()
		{
			registerTileEntity(TileEntityVisReplenisher.class, "vis_replenisher");
		}

		private static void registerTileEntity(Class<? extends TileEntity> teClass, String name)
		{
			GameRegistry.registerTileEntity(teClass, new ResourceLocation("magicalautomata", name));
		}
	}
}
