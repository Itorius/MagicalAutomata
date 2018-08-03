package com.thelastcog.magicalautomata.proxy;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.thelastcog.magicalautomata.common.container.ContainerAuraManipulator;
import com.thelastcog.magicalautomata.common.container.ContainerPoweredEssentiaSmeltery;
import com.thelastcog.magicalautomata.common.container.gui.GuiContainerAuraManipulator;
import com.thelastcog.magicalautomata.common.container.gui.GuiContainerPoweredEssentiaSmeltery;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityVisReplenisher;

public class GuiProxy implements IGuiHandler
{
	@Nullable @Override public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityPoweredEssentiaSmelter)
			return new ContainerPoweredEssentiaSmeltery(player.inventory, (TileEntityPoweredEssentiaSmelter)te);
		if (te instanceof TileEntityFluxScrubber)
			return new ContainerAuraManipulator(player.inventory, te);
		if (te instanceof TileEntityVisReplenisher)
			return new ContainerAuraManipulator(player.inventory, te);

		return null;
	}

	@Nullable @Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityPoweredEssentiaSmelter)
		{
			TileEntityPoweredEssentiaSmelter containerTileEntity = (TileEntityPoweredEssentiaSmelter)te;
			return new GuiContainerPoweredEssentiaSmeltery(containerTileEntity, new ContainerPoweredEssentiaSmeltery(player.inventory, containerTileEntity));
		}
		if (te instanceof TileEntityFluxScrubber)
		{
			TileEntityFluxScrubber containerTileEntity = (TileEntityFluxScrubber)te;
			return new GuiContainerAuraManipulator(containerTileEntity, new ContainerAuraManipulator(player.inventory, containerTileEntity));
		}
		if (te instanceof TileEntityVisReplenisher)
		{
			TileEntityVisReplenisher containerTileEntity = (TileEntityVisReplenisher)te;
			return new GuiContainerAuraManipulator(containerTileEntity, new ContainerAuraManipulator(player.inventory, containerTileEntity));
		}
		return null;
	}
}
