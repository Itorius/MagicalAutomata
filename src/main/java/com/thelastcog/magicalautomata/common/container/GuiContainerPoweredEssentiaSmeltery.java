package com.thelastcog.magicalautomata.common.container;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import com.thelastcog.magicalautomata.MagicalAutomata;
import com.thelastcog.magicalautomata.common.tileentity.TileEntityPoweredEssentiaSmelter;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.gui.GuiSmelter;
import thaumcraft.common.tiles.essentia.TileSmelter;

public class GuiContainerPoweredEssentiaSmeltery extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_smelter.png");

    private TileEntityPoweredEssentiaSmelter te;

    public GuiContainerPoweredEssentiaSmeltery(TileEntityPoweredEssentiaSmelter tileEntity, ContainerPoweredEssentiaSmeltery container) {
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
    }
}
