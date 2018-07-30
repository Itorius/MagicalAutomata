package com.thelastcog.magicalautomata;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.thelastcog.magicalautomata.common.blocks.ModBlocks;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

@Mod(modid = MagicalAutomata.MODID, name = MagicalAutomata.NAME, version = MagicalAutomata.VERSION, acceptedMinecraftVersions = MagicalAutomata.MC_VERSION)
public class MagicalAutomata
{
	public static final String MODID = "magicalautomata";
	public static final String NAME = "Magical Automata";
	public static final String VERSION = "0.0.1";
	public static final String MC_VERSION = "[1.12]";

	@Mod.Instance(MagicalAutomata.MODID)
	public static MagicalAutomata instance;

	private static Logger logger;

	public final CreativeTabs tabMagicalAutomata = new CreativeTabs("tabMagicalAutomata")
	{
		@Override
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ModBlocks.vis_replenisher);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		ResearchCategories.registerCategory("MAGICAL_AUTOMATA", null, new AspectList(),
				new ResourceLocation("thaumcraft", "textures/research/cat_alchemy.png"),
				new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_1.jpg"),
				new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png"));
		ThaumcraftApi.registerResearchLocation(new ResourceLocation(MODID, "research/all"));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}

