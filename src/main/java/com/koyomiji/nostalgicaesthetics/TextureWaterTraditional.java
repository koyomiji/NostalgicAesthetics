package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;

/*
 * This code has been ported from Minecraft 1.4.7.
 * The copyright of the original code belongs to Mojang AB.
 */
public class TextureWaterTraditional extends TextureAtlasSprite {
  protected float[] red = new float[256];
  protected float[] green = new float[256];
  protected float[] blue = new float[256];
  protected float[] alpha = new float[256];
  public int[] imageData = new int[256];

  public TextureWaterTraditional(String p_i1282_1_) {
    super(p_i1282_1_);
  }

  @Override
  public void updateAnimation() {
    this.tickCounter++;

    for (int $1 = 0; $1 < 16; $1++) {
      for (int $2 = 0; $2 < 16; $2++) {
        float $3 = 0.0F;

        for (int $4 = $1 - 1; $4 <= $1 + 1; $4++) {
          int $5 = $4 & 15;
          int $6 = $2 & 15;
          $3 += this.red[$5 + $6 * 16];
        }

        this.green[$1 + $2 * 16] = $3 / 3.3F + this.blue[$1 + $2 * 16] * 0.8F;
      }
    }

    for (int $12 = 0; $12 < 16; $12++) {
      for (int $14 = 0; $14 < 16; $14++) {
        this.blue[$12 + $14 * 16] = this.blue[$12 + $14 * 16] + this.alpha[$12 + $14 * 16] * 0.05F;
        if (this.blue[$12 + $14 * 16] < 0.0F) {
          this.blue[$12 + $14 * 16] = 0.0F;
        }

        this.alpha[$12 + $14 * 16] = this.alpha[$12 + $14 * 16] - 0.1F;
        if (Math.random() < 0.05) {
          this.alpha[$12 + $14 * 16] = 0.5F;
        }
      }
    }

    float[] $13 = this.green;
    this.green = this.red;
    this.red = $13;

    for (int $15 = 0; $15 < 256; $15++) {
      float $16 = this.red[$15];
      if ($16 > 1.0F) {
        $16 = 1.0F;
      }

      if ($16 < 0.0F) {
        $16 = 0.0F;
      }

      float $17 = $16 * $16;
      int $18 = (int) (32.0F + $17 * 32.0F);
      int $19 = (int) (50.0F + $17 * 64.0F);
      int $7 = 255;
      int $8 = (int) (146.0F + $17 * 50.0F);

      if (Minecraft.getMinecraft().gameSettings.anaglyph) {
        int $9 = ($18 * 30 + $19 * 59 + $7 * 11) / 100;
        int $10 = ($18 * 30 + $19 * 70) / 100;
        int $11 = ($18 * 30 + $7 * 70) / 100;
        $18 = $9;
        $19 = $10;
        $7 = $11;
      }

      this.imageData[$15] = TextureHelper.packRGBA($18, $19, $7, $8);
    }

    int mipmapCount = Minecraft.getMinecraft().gameSettings.mipmapLevels;
    int[][] mipmaps = new int[mipmapCount + 1][];
    mipmaps[0] = imageData;
    mipmaps = TextureUtil.generateMipmapData(mipmapCount, this.getIconWidth(), mipmaps);

    for (int x = 0; x < this.width / 16; x++) {
      for (int y = 0; y < this.height / 16; y++) {
        TextureUtil.uploadTextureMipmap(mipmaps, 16, 16, this.originX + 16 * x, this.originY + 16 * y, false, false);
      }
    }
  }
}
