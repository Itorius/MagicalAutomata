package com.thelastcog.magicalautomata;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.thelastcog.magicalautomata.common.block.ModBlocks;
import com.thelastcog.magicalautomata.proxy.CommonProxy;

@Mod(modid = MAInfo.MODID, name = MAInfo.NAME, version = MAInfo.VERSION, acceptedMinecraftVersions = MAInfo.MC_VERSION, dependencies = MAInfo.DEPENDENCIES)
public class MagicalAutomata
{
	@Mod.Instance(MAInfo.MODID)
	public static MagicalAutomata instance;

	@SidedProxy(clientSide = "com.thelastcog.magicalautomata.proxy.ClientProxy", serverSide = "com.thelastcog.magicalautomata.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static Logger logger;

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
		proxy.preInit(event);
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}

