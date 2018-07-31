package com.thelastcog.magicalautomata.common.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;

public class GuiContainerPoweredEssentiaSmeltery extends GuiContainer
{
	public static final int WIDTH = 180;
	public static final int HEIGHT = 152;

	private static final ResourceLocation background = new ResourceLocation(MagicalAutomata.MODID, "textures/gui/testcontainer.png");

	public GuiContainerPoweredEssentiaSmeltery(TileEntityPoweredEssentiaSmelter tileEntity, ContainerPoweredEssentiaSmeltery container)
	{
		super(container);

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
