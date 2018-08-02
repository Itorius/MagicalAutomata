package com.thelastcog.magicalautomata.common.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.lwjgl.opengl.GL11;

import com.thelastcog.magicalautomata.MAInfo;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;

public class GuiContainerPoweredEssentiaSmeltery extends GuiContainer
{
	ResourceLocation background = new ResourceLocation(MAInfo.MODID, "textures/gui/powered_essentia_smelter_gui.png");

	private TileEntityPoweredEssentiaSmelter te;

	public GuiContainerPoweredEssentiaSmeltery(TileEntityPoweredEssentiaSmelter tileEntity, ContainerPoweredEssentiaSmeltery container)
	{
		super(container);

		te = tileEntity;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(this.background);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		GL11.glEnable(3042);
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		float i1 = te.getCookProgressScaled(46);
		drawTexturedModalRect(k + 98, (int) (l + 13 + 46 - i1), 216, (int) (46 - i1), 9, (int) (i1));

		i1 = te.getEnergyScaled(46);
		drawTexturedModalRect(k + 114, (int) (l + 13 + 46 - i1), 216, (int) (92 - i1), 9, (int) (i1));

		i1 = te.getVisScaled(48);
		drawTexturedModalRect(k + 53, (int) (l + 12 + 48 - i1), 200, (int) (48 - i1), 8, (int) (i1));
		drawTexturedModalRect(k + 52, l + 8, 232, 0, 10, 55);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (isPointInRegion(114, 13, 9, 46, mouseX, mouseY))
		{
			IEnergyStorage energy = te.getCapability(CapabilityEnergy.ENERGY, null);
			drawHoveringText("Energy: " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored(), mouseX - guiLeft, mouseY - guiTop);
		}
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
}
