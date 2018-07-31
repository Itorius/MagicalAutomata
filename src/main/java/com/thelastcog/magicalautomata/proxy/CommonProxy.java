package com.thelastcog.magicalautomata.proxy;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.blocks.ModBlocks;
import com.thelastcog.magicalautomata.compat.CompatHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        CompatHandler.registerTOP();
    }

    public void init(FMLInitializationEvent event)
    {
        ResearchCategories.registerCategory("MAGICAL_AUTOMATA", null, new AspectList(),
                new ResourceLocation("thaumcraft", "textures/research/cat_alchemy.png"),
                new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_1.jpg"),
                new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(MagicalAutomata.MODID, "research/all"));
        MagicalAutomata.logger.info("Registered Thaumcraft category");
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        ModBlocks.RegistrationHandler.registerBlocks(event);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        ModBlocks.RegistrationHandler.registerItemBlocks(event);
    }
}
