package com.thelastcog.magicalautomata.compat;

import com.thelastcog.magicalautomata.MagicalAutomata;
import net.minecraftforge.fml.common.Loader;

public class CompatHandler
{
    public static void registerTOP()
    {
        if (Loader.isModLoaded("theoneprobe"))
        {
            TOPCompatibility.register();
        }
    }
}
