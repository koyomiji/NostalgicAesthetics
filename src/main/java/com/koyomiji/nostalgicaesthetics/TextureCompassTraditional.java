package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.awt.image.BufferedImage;

/*
 * This code has been ported from Minecraft 1.4.7.
 * The copyright of the original code belongs to Mojang AB.
 */
public class TextureCompassTraditional extends TextureCompass {
  public int[] imageData = new int[256];
  private final int[] compassIconImageData = new int[256];

  public TextureCompassTraditional(String p_i1286_1_) {
    super(p_i1286_1_);

    BufferedImage image = TextureHelper.readImage(new ResourceLocation(NostalgicAesthetics.MODID, "textures/items/compass.png"));
    image.getRGB(0, 0, 16, 16, compassIconImageData, 0, 16);
  }

  @Override
  public void updateCompass(World p_updateCompass_1_, double p_updateCompass_2_, double p_updateCompass_4_, double p_updateCompass_6_, boolean p_updateCompass_8_, boolean p_updateCompass_9_) {
    if (!this.framesTextureData.isEmpty()) {
      for (int $10 = 0; $10 < 256; $10++) {
        int $11 = this.compassIconImageData[$10] >> 24 & 0xFF;
        int $12 = this.compassIconImageData[$10] >> 16 & 0xFF;
        int $13 = this.compassIconImageData[$10] >> 8 & 0xFF;
        int $14 = this.compassIconImageData[$10] >> 0 & 0xFF;

        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
          int $15 = ($12 * 30 + $13 * 59 + $14 * 11) / 100;
          int $16 = ($12 * 30 + $13 * 70) / 100;
          int $17 = ($12 * 30 + $14 * 70) / 100;
          $12 = $15;
          $13 = $16;
          $14 = $17;
        }

        this.imageData[$10] = TextureHelper.packRGBA($12, $13, $14, $11);
      }

      double $27 = 0.0;
      if (p_updateCompass_1_ != null && !p_updateCompass_8_) {
        ChunkCoordinates $28 = p_updateCompass_1_.getSpawnPoint();
        double $31 = (double) $28.posX - p_updateCompass_2_;
        double $33 = (double) $28.posZ - p_updateCompass_4_;
        p_updateCompass_6_ %= 360.0;
        $27 = (p_updateCompass_6_ - 90.0) * Math.PI / 180.0 - Math.atan2($33, $31);
        if (!p_updateCompass_1_.provider.isSurfaceWorld()) {
          $27 = Math.random() * 3.1415927410125732 * 2.0;
        }
      }

      if (p_updateCompass_9_) {
        this.currentAngle = $27;
      } else {
        double $29 = $27 - this.currentAngle;

        while ($29 < -Math.PI) {
          $29 += Math.PI * 2;
        }

        while ($29 >= Math.PI) {
          $29 -= Math.PI * 2;
        }

        if ($29 < -1.0) {
          $29 = -1.0;
        }

        if ($29 > 1.0) {
          $29 = 1.0;
        }

        this.angleDelta += $29 * 0.1;
        this.angleDelta *= 0.8;
        this.currentAngle += this.angleDelta;
      }

      double $30 = Math.sin(this.currentAngle);
      double $32 = Math.cos(this.currentAngle);

      for (int $34 = -4; $34 <= 4; $34++) {
        int $36 = (int) (8.5 + $32 * (double) $34 * 0.3);
        int $18 = (int) (7.5 - $30 * (double) $34 * 0.3 * 0.5);
        int $19 = $18 * 16 + $36;
        int $20 = 100;
        int $21 = 100;
        int $22 = 100;
        short $23 = 255;

        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
          int $24 = ($20 * 30 + $21 * 59 + $22 * 11) / 100;
          int $25 = ($20 * 30 + $21 * 70) / 100;
          int $26 = ($20 * 30 + $22 * 70) / 100;
          $20 = $24;
          $21 = $25;
          $22 = $26;
        }

        this.imageData[$19] = TextureHelper.packRGBA($20, $21, $22, $23);
      }

      for (int $35 = -8; $35 <= 16; $35++) {
        int $37 = (int) (8.5 + $30 * (double) $35 * 0.3);
        int $38 = (int) (7.5 + $32 * (double) $35 * 0.3 * 0.5);
        int $39 = $38 * 16 + $37;
        int $40 = $35 >= 0 ? 255 : 100;
        int $41 = $35 >= 0 ? 20 : 100;
        int $42 = $35 >= 0 ? 20 : 100;
        short $43 = 255;

        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
          int $44 = ($40 * 30 + $41 * 59 + $42 * 11) / 100;
          int $45 = ($40 * 30 + $41 * 70) / 100;
          int $46 = ($40 * 30 + $42 * 70) / 100;
          $40 = $44;
          $41 = $45;
          $42 = $46;
        }

        this.imageData[$39] = TextureHelper.packRGBA($40, $41, $42, $43);
      }

      TextureUtil.uploadTextureMipmap(new int[][]{imageData}, this.width, this.height, this.originX, this.originY, false, false);
    }
  }
}
