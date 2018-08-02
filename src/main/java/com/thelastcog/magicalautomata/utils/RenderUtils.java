package com.thelastcog.magicalautomata.utils;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import org.lwjgl.opengl.GL11;

public class RenderUtils
{
	public static void drawTexturedQuad(float x, float y, float width, float height, float texCoordX, float texCoordY, float zLevel)
	{
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		BufferBuilder builder = Tessellator.getInstance().getBuffer();
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		builder.pos((double)(x + 0.0F), (double)(y + height), zLevel).tex((double)((texCoordX + 0.0F) * var7), (double)((texCoordY + height) * var8)).endVertex();
		builder.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((texCoordX + width) * var7), (double)((texCoordY + height) * var8)).endVertex();
		builder.pos((double)(x + width), (double)(y + 0.0F), zLevel).tex((double)((texCoordX + width) * var7), (double)((texCoordY + 0.0F) * var8)).endVertex();
		builder.pos((double)(x + 0.0F), (double)(y + 0.0F), zLevel).tex((double)((texCoordX + 0.0F) * var7), (double)((texCoordY + 0.0F) * var8)).endVertex();
		Tessellator.getInstance().draw();
	}
}
