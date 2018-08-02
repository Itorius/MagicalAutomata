package com.thelastcog.magicalautomata.common.container.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import org.lwjgl.opengl.GL11;

import com.thelastcog.magicalautomata.MAInfo;
import com.thelastcog.magicalautomata.common.container.ContainerFluxScrubber;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityFluxScrubber;
import com.thelastcog.magicalautomata.utils.RenderUtils;

import thaumcraft.api.aura.AuraHelper;

public class GuiContainerFluxScrubber extends GuiContainer
{
	ResourceLocation background = new ResourceLocation(MAInfo.MODID, "textures/gui/aura_manipulator_gui.png");

	private TileEntityFluxScrubber te;

	public GuiContainerFluxScrubber(TileEntityFluxScrubber tileEntity, ContainerFluxScrubber container)
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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (isPointInRegion(106, 13, 9, 46, mouseX, mouseY))
		{
			IEnergyStorage energy = te.getCapability(CapabilityEnergy.ENERGY, null);
			drawHoveringText("Energy: " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored(), mouseX - guiLeft, mouseY - guiTop);
		}
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(this.background);
		//int k = (width - xSize) / 2;
		//int l = (height - ySize) / 2;

		GL11.glEnable(GL11.GL_BLEND);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		float height = te.getEnergyScaled(46);
		RenderUtils.drawTexturedQuad(guiLeft + 106, guiTop + 59 - height, 216, 46 - height, 9, height, zLevel);

		renderAuraDisplay(0);
	}

	private void renderAuraDisplay(float partialTicks)
	{
		mc.renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/gui/hud.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		float base = MathHelper.clamp((float)AuraHelper.getAuraBase(te.getWorld(), te.getPos()) / 525.0F, 0.0F, 1.0F);
		float vis = MathHelper.clamp(AuraHelper.getVis(te.getWorld(), te.getPos()) / 525.0F, 0.0F, 1.0F);
		float flux = MathHelper.clamp(AuraHelper.getFlux(te.getWorld(), te.getPos()) / 525.0F, 0.0F, 1.0F);
		float count = (float)mc.ingameGUI.getUpdateCounter() + partialTicks;
		float count2 = (float)mc.ingameGUI.getUpdateCounter() / 3.0F + partialTicks;

		float startY;
		if (flux + vis > 1.0F)
		{
			startY = 1.0F / (flux + vis);
			base *= startY;
			vis *= startY;
			flux *= startY;
		}

		startY = 10.0F + (1.0F - vis) * 64.0F;
		if (vis > 0.0F)
		{
			// bar texture
			GL11.glPushMatrix();
			GL11.glColor4f(0.7F, 0.4F, 0.9F, 1.0F);
			RenderUtils.drawTexturedQuad(guiLeft + 5f, guiTop + startY, 88f, 56f, 8f, 64f * vis, zLevel);
			GL11.glPopMatrix();

			// overlay animation
			GL11.glPushMatrix();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			RenderUtils.drawTexturedQuad(guiLeft + 5f, guiTop + startY, 96f, (56f + (count % 64.0f)), 8f, 64f * vis, zLevel);
			GL11.glBlendFunc(770, 771);
			GL11.glPopMatrix();

			// text
			GL11.glPushMatrix();
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			mc.fontRenderer.drawString(String.format("%.1f", AuraHelper.getVis(te.getWorld(), te.getPos())), (guiLeft + 16) * 2, (int)(guiTop + startY) * 2, 15641343);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/gui/hud.png"));
		}

		if (flux > 0.0F)
		{
			startY = 10.0F + (1.0F - flux - vis) * 64.0F;

			// bar texture
			GL11.glPushMatrix();
			GL11.glColor4f(0.25F, 0.1F, 0.3F, 1.0F);
			RenderUtils.drawTexturedQuad(guiLeft + 5f, guiTop + startY, 88, 56f, 8f, 64f * flux, zLevel);
			GL11.glPopMatrix();

			// overlay animation
			GL11.glPushMatrix();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor4f(0.7F, 0.4F, 1.0F, 0.5F);
			RenderUtils.drawTexturedQuad(guiLeft + 5f, guiTop + startY, 104f, (int)(120f - count2 % 64.0f), 8f, 64f * flux, zLevel);
			GL11.glBlendFunc(770, 771);
			GL11.glPopMatrix();

			// text
			GL11.glPushMatrix();
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			mc.fontRenderer.drawString(String.format("%.1f", AuraHelper.getFlux(te.getWorld(), te.getPos())), (guiLeft + 16) * 2, (int)(guiTop + startY - 4) * 2, 11145659);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(new ResourceLocation("thaumcraft", "textures/gui/hud.png"));
		}

		// bar background
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtils.drawTexturedQuad(guiLeft + 1f, guiTop + 1f, 72f, 48f, 16f, 80f, zLevel);
		GL11.glPopMatrix();

		//
		startY = 8.0F + (1.0F - base) * 64.0F;
		GL11.glPushMatrix();
		RenderUtils.drawTexturedQuad(guiLeft + 2f, guiTop + startY, 117f, 61f, 14f, 5f, zLevel);
		GL11.glPopMatrix();
	}
}
