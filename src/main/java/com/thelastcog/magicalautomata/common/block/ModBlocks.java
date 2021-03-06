package com.thelastcog.magicalautomata.common.block;

import java.util.HashSet;
import java.util.Set;

import com.thelastcog.magicalautomata.MAInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.google.common.base.Preconditions;
import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityCreativeEnergySource;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityVisReplenisher;

@GameRegistry.ObjectHolder(MAInfo.MODID)
public class ModBlocks
{
	public static final BlockVisReplenisher vis_replenisher = null;
	public static final BlockFluxScrubber flux_scrubber = null;
	public static final BlockCreativeEnergySource creative_energy_source = null;
	public static final BlockPoweredEssentiaSmelter powered_essentia_smelter = null;

	public static class RegistrationHandler
	{
		public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

		public static void registerBlocks(final RegistryEvent.Register<Block> event)
		{
			final IForgeRegistry<Block> registry = event.getRegistry();

			final Block[] blocks = {
					new BlockVisReplenisher(),
					new BlockFluxScrubber(),
					new BlockCreativeEnergySource(),
					new BlockPoweredEssentiaSmelter()
			};
			registry.registerAll(blocks);
			registerTileEntities();

			MagicalAutomata.logger.info("Registered block");
		}

		public static void registerItemBlocks(final RegistryEvent.Register<Item> event)
		{
			final ItemBlock[] items = {
					new ItemBlock(vis_replenisher),
					new ItemBlock(flux_scrubber),
					new ItemBlock(creative_energy_source),
					new ItemBlock(powered_essentia_smelter)
			};

			final IForgeRegistry<Item> registry = event.getRegistry();

			for (final ItemBlock item : items)
			{
				final Block block = item.getBlock();
				final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
				registry.register(item.setRegistryName(registryName));
				ITEM_BLOCKS.add(item);
			}

			MagicalAutomata.logger.info("Registered item-block");
		}

		private static void registerTileEntities()
		{
			registerTileEntity(TileEntityVisReplenisher.class, "vis_replenisher");
			registerTileEntity(TileEntityFluxScrubber.class, "flux_scrubber");
			registerTileEntity(TileEntityCreativeEnergySource.class, "creative_energy_source");
			registerTileEntity(TileEntityPoweredEssentiaSmelter.class, "powered_essentia_smelter");

			MagicalAutomata.logger.info("Registered tile entities");
		}

		private static void registerTileEntity(Class<? extends TileEntity> teClass, String name)
		{
			GameRegistry.registerTileEntity(teClass, new ResourceLocation("magicalautomata", name));
		}
	}
}
