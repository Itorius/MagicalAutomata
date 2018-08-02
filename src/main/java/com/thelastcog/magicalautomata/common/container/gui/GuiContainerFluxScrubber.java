package com.thelastcog.magicalautomata.common.container.gui;

import com.thelastcog.magicalautomata.MAInfo;
import com.thelastcog.magicalautomata.common.container.ContainerPoweredEssentiaSmeltery;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.lwjgl.opengl.GL11;
import sun.net.www.MimeEntry;
import thaumcraft.Thaumcraft;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.lib.events.HudHandler;
import thaumcraft.common.items.armor.ItemGoggles;
import thaumcraft.common.items.tools.ItemThaumometer;
import thaumcraft.common.world.aura.AuraHandler;
import thaumcraft.proxies.ClientProxy;
import thaumcraft.proxies.ProxyGUI;

public class GuiContainerFluxScrubber extends GuiContainer {
    ResourceLocation background = new ResourceLocation(MAInfo.MODID, "textures/gui/aura_manipulator_gui.png");

    private TileEntityPoweredEssentiaSmelter te;

    public GuiContainerFluxScrubber(TileEntityPoweredEssentiaSmelter tileEntity, ContainerPoweredEssentiaSmeltery container) {
        super(container);

        te = tileEntity;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(this.background);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        GL11.glEnable(3042);
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

        float i1 = te.getEnergyScaled(46);
        drawTexturedModalRect(k + 106, (int) (l + 13 + 46 - i1), 216, (int) (46 - i1), 9, (int) (i1));

		/*i1 = te.getEnergyScaled(46);
		drawTexturedModalRect(k + 114, (int) (l + 13 + 46 - i1), 216, (int) (92 - i1), 9, (int) (i1));
*/

        i1 = te.getVisScaled(48);
        drawTexturedModalRect(k + 53, (int) (l + 12 + 48 - i1), 200, (int) (48 - i1), 8, (int) (i1));
        drawTexturedModalRect(k + 52, l + 8, 232, 0, 10, 55);
    }

    void renderThaumometerHud(Minecraft mc, float partialTicks, EntityPlayer player, long time, int ww, int hh) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float base = MathHelper.clamp((float) AuraHelper.getAuraBase(te.getWorld(), te.getPos()) / 525.0F, 0.0F, 1.0F);
        float vis = MathHelper.clamp(AuraHelper.getVis(te.getWorld(), te.getPos()) / 525.0F, 0.0F, 1.0F);
        float flux = MathHelper.clamp(AuraHelper.getFlux(te.getWorld(), te.getPos()) / 525.0F, 0.0F, 1.0F);
        float count = (float) Minecraft.func_71410_x().func_175606_aa().field_70173_aa + partialTicks;
        float count2 = (float) Minecraft.func_71410_x().func_175606_aa().field_70173_aa / 3.0F + partialTicks;

        float start;
        if (flux + vis > 1.0F) {
            start = 1.0F / (flux + vis);
            base *= start;
            vis *= start;
            flux *= start;
        }

        start = 10.0F + (1.0F - vis) * 64.0F;
        String msg;
//mc.renderEngine.bindTexture();
        if (vis > 0.0F) {
            GL11.glPushMatrix();
            // set color
            GL11.glColor4f(0.7F, 0.4F, 0.9F, 1.0F);
            // setposition
            GL11.glTranslated(5.0D, (double) start, 0.0D);
            // set Y-scale
            GL11.glScaled(1.0D, (double) vis, 1.0D);
            // draw from gui/hud
            UtilsFX.drawTexturedQuad(0.0F, 0.0F, 88.0F, 56.0F, 8.0F, 64.0F, -90.0D);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glBlendFunc(770, 1);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
            GL11.glTranslated(5.0D, (double) start, 0.0D);
            UtilsFX.drawTexturedQuad(0.0F, 0.0F, 96.0F, 56.0F + count % 64.0F, 8.0F, vis * 64.0F, -90.0D);
            GL11.glBlendFunc(770, 771);
            GL11.glPopMatrix();

				/*GL11.glPushMatrix();
				GL11.glTranslated(16.0D, (double) start, 0.0D);
				GL11.glScaled(0.5D, 0.5D, 0.5D);
				msg = this.secondsFormatter.format((double) AuraHelper.getVis(te.getWorld(), te.getPos()));
				mc.field_71456_v.func_73731_b(mc.field_71466_p, msg, 0, 0, 15641343);
				GL11.glPopMatrix();
				mc.field_71446_o.func_110577_a(this.HUD);
				mc.fontRenderer.drawString( msg, 0, 0, 15641343);*/
        }

		/*if (flux > 0.0F)
		{
			start = 10.0F + (1.0F - flux - vis) * 64.0F;
			GL11.glPushMatrix();
			GL11.glColor4f(0.25F, 0.1F, 0.3F, 1.0F);
			GL11.glTranslated(5.0D, (double) start, 0.0D);
			GL11.glScaled(1.0D, (double) flux, 1.0D);
			UtilsFX.drawTexturedQuad(0.0F, 0.0F, 88.0F, 56.0F, 8.0F, 64.0F, -90.0D);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glBlendFunc(770, 1);
			GL11.glColor4f(0.7F, 0.4F, 1.0F, 0.5F);
			GL11.glTranslated(5.0D, (double) start, 0.0D);
			UtilsFX.drawTexturedQuad(0.0F, 0.0F, 104.0F, 120.0F - count2 % 64.0F, 8.0F, flux * 64.0F, -90.0D);
			GL11.glBlendFunc(770, 771);
			GL11.glPopMatrix();

		/*	GL11.glPushMatrix();
			GL11.glTranslated(16.0D, (double) (start - 4.0F), 0.0D);
			GL11.glScaled(0.5D, 0.5D, 0.5D);
			msg = this.secondsFormatter.format(AuraHelper.getFlux(te.getWorld(), te.getPos()));
			mc..func_73731_b(mc.field_71466_p, msg, 0, 0, 11145659);
			GL11.glPopMatrix();
			mc.field_71446_o.func_110577_a(this.HUD);
		}*/

        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        UtilsFX.drawTexturedQuad(1.0F, 1.0F, 72.0F, 48.0F, 16.0F, 80.0F, -90.0D);
        GL11.glPopMatrix();
        start = 8.0F + (1.0F - base) * 64.0F;
        GL11.glPushMatrix();
        UtilsFX.drawTexturedQuad(2.0F, start, 117.0F, 61.0F, 14.0F, 5.0F, -90.0D);
        GL11.glPopMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        if (isPointInRegion(114, 13, 9, 46, mouseX, mouseY)) {
            IEnergyStorage energy = te.getCapability(CapabilityEnergy.ENERGY, null);
            drawHoveringText("Energy: " + energy.getEnergyStored() + "/" + energy.getMaxEnergyStored(), mouseX - guiLeft, mouseY - guiTop);
        }
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }
}
