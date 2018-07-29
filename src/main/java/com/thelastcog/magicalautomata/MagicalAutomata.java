package com.thelastcog.magicalautomata;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

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

    static final CreativeTabs tabMagicalAutomata = (new CreativeTabs("tabMagicalAutomata")
    {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(Items.BAKED_POTATO);
        }
    });

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}
