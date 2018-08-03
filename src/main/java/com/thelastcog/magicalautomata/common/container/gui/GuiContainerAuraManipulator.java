package com.thelastcog.magicalautomata.common.container.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import org.lwjgl.opengl.GL11;

import com.thelastcog.magicalautomata.MAInfo;
import com.thelastcog.magicalautomata.common.container.ContainerAuraManipulator;
import com.thelastcog.magicalautomata.utils.MathUtils;
import com.thelastcog.magicalautomata.utils.RenderUtils;

import thaumcraft.api.aura.AuraHelper;

public class GuiContainerAuraManipulator extends GuiContainer
{
	private ResourceLocation background = new ResourceLocation(MAInfo.MODID, "textures/gui/aura_manipulator_gui.png");
	private ResourceLocation thaumcraftHUD = new ResourceLocation("thaumcraft", "textures/gui/hud.png");

	private TileEntity te;

	public GuiContainerAuraManipulator(TileEntity tileEntity, ContainerAuraManipulator container)
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
			drawHoveringText(MathUtils.ToSI(energy.getEnergyStored(), null) + "/" + MathUtils.ToSI(energy.getMaxEnergyStored(), null) + "RF", mouseX - guiLeft, mouseY - guiTop);
		}

		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1f, 1f, 1f, 1f);
		mc.renderEngine.bindTexture(this.background);

		GL11.glEnable(GL11.GL_BLEND);
		RenderUtils.drawTexturedQuad(guiLeft, guiTop, xSize, ySize, 0f, 0f, zLevel);

		IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, null);
		float height = (energyStorage.getEnergyStored() / (float)energyStorage.getMaxEnergyStored()) * 46;
		RenderUtils.drawTexturedQuad(guiLeft + 106, guiTop + 59f - height, 9f, height, 216f, 46f - height, zLevel);

		renderAuraDisplay(partialTicks);
	}

	private void renderAuraDisplay(float partialTicks)
	{
		GL11.glColor4f(1f, 1f, 1f, 1f);

		mc.renderEngine.bindTexture(background);
		RenderUtils.drawTexturedQuad(guiLeft + 60f, guiTop + 8f, 10f, 56f, 232f, 0f, zLevel);

		mc.renderEngine.bindTexture(thaumcraftHUD);

		float base = MathHelper.clamp((float)AuraHelper.getAuraBase(te.getWorld(), te.getPos()) / 525f, 0f, 1f);
		float vis = MathHelper.clamp(AuraHelper.getVis(te.getWorld(), te.getPos()) / 525f, 0f, 1f);
		float flux = MathHelper.clamp(AuraHelper.getFlux(te.getWorld(), te.getPos()) / 525f, 0f, 1f);
		float count = (float)mc.ingameGUI.getUpdateCounter() + partialTicks;

		float startY;
		if (flux + vis > 1f)
		{
			startY = 1f / (flux + vis);
			base *= startY;
			vis *= startY;
			flux *= startY;
		}

		startY = 12f + (1f - vis) * 48f;
		if (vis > 0f)
		{
			GL11.glPushMatrix();
			GL11.glColor4f(0.7F, 0.4F, 0.9F, 1.0F);
			RenderUtils.drawTexturedQuad(guiLeft + 61f, guiTop + startY, 8f, 48f * vis, 88f, 56f, zLevel);
			GL11.glPopMatrix();

			mc.renderEngine.bindTexture(background);
			GL11.glPushMatrix();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
			RenderUtils.drawTexturedQuad(guiLeft + 61f, guiTop + startY, 8f, 48f * vis, 200f, 52f + count % 48f, zLevel);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(thaumcraftHUD);

			GL11.glPushMatrix();
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			String msg = String.format("%.0f", AuraHelper.getVis(te.getWorld(), te.getPos()));
			mc.fontRenderer.drawString(msg, (int)((guiLeft + 88 - mc.fontRenderer.getStringWidth(msg) * 0.25f) * 2), (int)((guiTop + 24 - mc.fontRenderer.FONT_HEIGHT * 0.5f) * 2), 15641343);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(thaumcraftHUD);
		}

		if (flux > 0f)
		{
			startY = 12f + (1f - flux - vis) * 48f;

			GL11.glPushMatrix();
			GL11.glColor4f(0.25F, 0.1F, 0.3F, 1.0F);
			RenderUtils.drawTexturedQuad(guiLeft + 61f, guiTop + startY, 8, 48f * flux, 88f, 56f, zLevel);
			GL11.glPopMatrix();

			mc.renderEngine.bindTexture(background);
			GL11.glPushMatrix();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor4f(0.7F, 0.4F, 1.0F, 0.5F);
			RenderUtils.drawTexturedQuad(guiLeft + 61f, guiTop + startY, 8f, 48f * flux, 208f, 100f - count % 48f, zLevel);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(thaumcraftHUD);

			GL11.glPushMatrix();
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			String msg = String.format("%.0f", AuraHelper.getFlux(te.getWorld(), te.getPos()));
			mc.fontRenderer.drawString(msg, (int)((guiLeft + 88 - fontRenderer.getStringWidth(msg) * 0.25f) * 2), (guiTop + 28) * 2, 11145659);
			GL11.glPopMatrix();
			mc.renderEngine.bindTexture(thaumcraftHUD);
		}

		startY = 10f + (1f - base) * 48f;
		GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
		RenderUtils.drawTexturedQuad(guiLeft + 58f, guiTop + startY, 14f, 5f, 117f, 61f, zLevel);
		GL11.glPopMatrix();
	}
}