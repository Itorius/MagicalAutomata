package com.thelastcog.magicalautomata.compat;

import com.thelastcog.magicalautomata.MagicalAutomata;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TOPCompatibility
{
    private static boolean registered;

    public static void register()
    {
        if (registered)
            return;
        registered = true;
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.thelastcog.magicalautomata.compat.TOPCompatibility$GetTheOneProbe");
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void>
    {
        public static ITheOneProbe probe;

        @Nullable
        @Override
        public Void apply(ITheOneProbe theOneProbe)
        {
            probe = theOneProbe;
            MagicalAutomata.logger.info("The One Probe functionality has been enabled");
            probe.registerProvider(new IProbeInfoProvider() {
                @Override
                public String getID()
                {
                    return "magicalautomata:default";
                }

                @Override
                public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
                {
                    if (blockState.getBlock() instanceof TOPInfoProvider)
                    {
                        TOPInfoProvider provider = (TOPInfoProvider)blockState.getBlock();
                        provider.addProbeInfo(mode, probeInfo, player, world, blockState, data);
                    }
                }
            });
            return null;
        }
    }

    public interface TOPInfoProvider
    {
        void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data);
    }
}
