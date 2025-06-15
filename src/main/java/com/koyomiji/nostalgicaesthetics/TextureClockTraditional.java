package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureClock;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;

/*
 * This code has been ported from Minecraft 1.4.7.
 * The copyright of the original code belongs to Mojang AB.
 */
public class TextureClockTraditional extends TextureClock {
  private double field_94239_h;
  private double field_94240_i;
  public int[] imageData = new int[256];
  private final int[] clockIconImageData = new int[256];
  private final int[] dialImageData = new int[256];

  public TextureClockTraditional(String p_i1285_1_) {
    super(p_i1285_1_);

    BufferedImage image = TextureHelper.readImage(new ResourceLocation(NostalgicAesthetics.MODID, "textures/items/clock.png"));
    image.getRGB(0, 0, 16, 16, clockIconImageData, 0, 16);
    image = TextureHelper.readImage(new ResourceLocation(NostalgicAesthetics.MODID, "textures/misc/dial.png"));
    image.getRGB(0, 0, 16, 16, dialImageData, 0, 16);
  }

  @Override
  public void updateAnimation() {
    if (!this.framesTextureData.isEmpty()) {
      Minecraft mc = Minecraft.getMinecraft();

      double $1 = 0.0;
      if (mc.theWorld != null && mc.thePlayer != null) {
        float $3 = mc.theWorld.getCelestialAngle(1.0F);
        $1 = -$3 * Math.PI * 2;
        if (!mc.theWorld.provider.isSurfaceWorld()) {
          $1 = Math.random() * Math.PI * 2;
        }
      }

      double $22 = $1 - this.field_94239_h;

      while ($22 < -Math.PI) {
        $22 += Math.PI * 2;
      }

      while ($22 >= Math.PI) {
        $22 -= Math.PI * 2;
      }

      if ($22 < -1.0) {
        $22 = -1.0;
      }

      if ($22 > 1.0) {
        $22 = 1.0;
      }

      this.field_94240_i += $22 * 0.1;
      this.field_94240_i *= 0.8;
      this.field_94239_h += this.field_94240_i;
      double $5 = Math.sin(this.field_94239_h);
      double $7 = Math.cos(this.field_94239_h);

      for (int $9 = 0; $9 < 256; $9++) {
        int $10 = this.clockIconImageData[$9] >> 24 & 0xFF;
        int $11 = this.clockIconImageData[$9] >> 16 & 0xFF;
        int $12 = this.clockIconImageData[$9] >> 8 & 0xFF;
        int $13 = this.clockIconImageData[$9] >> 0 & 0xFF;
        if ($11 == $13 && $12 == 0 && $13 > 0) {
          double $14 = -((double) ($9 % 16) / 15.0 - 0.5);
          double $16 = (double) ($9 / 16) / 15.0 - 0.5;
          int $18 = $11;
          int $19 = (int) (($14 * $7 + $16 * $5 + 0.5) * 16.0);
          int $20 = (int) (($16 * $7 - $14 * $5 + 0.5) * 16.0);
          int $21 = ($19 & 15) + ($20 & 15) * 16;
          $10 = this.dialImageData[$21] >> 24 & 0xFF;
          $11 = (this.dialImageData[$21] >> 16 & 0xFF) * $11 / 255;
          $12 = (this.dialImageData[$21] >> 8 & 0xFF) * $18 / 255;
          $13 = (this.dialImageData[$21] >> 0 & 0xFF) * $18 / 255;
        }

        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
          int $23 = ($11 * 30 + $12 * 59 + $13 * 11) / 100;
          int $15 = ($11 * 30 + $12 * 70) / 100;
          int $24 = ($11 * 30 + $13 * 70) / 100;
          $11 = $23;
          $12 = $15;
          $13 = $24;
        }

        this.imageData[$9] = TextureHelper.packRGBA($11, $12, $13, $10);
      }

      TextureUtil.uploadTextureMipmap(new int[][]{imageData}, this.width, this.height, this.originX, this.originY, false, false);
    }
  }
}
