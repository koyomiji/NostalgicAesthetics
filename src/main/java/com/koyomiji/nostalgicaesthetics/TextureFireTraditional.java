package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;

/*
 * This code has been ported from Minecraft 1.4.7.
 * The copyright of the original code belongs to Mojang AB.
 */
public class TextureFireTraditional extends TextureAtlasSprite {
  protected float[] field_76869_g = new float[320];
  protected float[] field_76870_h = new float[320];
  public int[] imageData = new int[256];

  public TextureFireTraditional(String p_i1282_1_) {
    super(p_i1282_1_);
  }

  @Override
  public void updateAnimation() {
    for (int $1 = 0; $1 < 16; $1++) {
      for (int $2 = 0; $2 < 20; $2++) {
        int $3 = 18;
        float $4 = this.field_76869_g[$1 + ($2 + 1) % 20 * 16] * (float) $3;

        for (int var5 = $1 - 1; var5 <= $1 + 1; var5++) {
          for (int var6 = $2; var6 <= $2 + 1; var6++) {
            if (var5 >= 0 && var6 >= 0 && var5 < 16 && var6 < 20) {
              $4 += this.field_76869_g[var5 + var6 * 16];
            }

            $3++;
          }
        }

        this.field_76870_h[$1 + $2 * 16] = $4 / ((float) $3 * 1.0600001F);

        if ($2 >= 19) {
          this.field_76870_h[$1 + $2 * 16] = (float) (Math.random() * Math.random() * Math.random() * 4.0 + Math.random() * 0.1F + 0.2F);
        }
      }
    }

    float[] $13 = this.field_76870_h;
    this.field_76870_h = this.field_76869_g;
    this.field_76869_g = $13;

    for (int $14 = 0; $14 < 256; $14++) {
      float $15 = this.field_76869_g[$14] * 1.8F;
      if ($15 > 1.0F) {
        $15 = 1.0F;
      }

      if ($15 < 0.0F) {
        $15 = 0.0F;
      }

      int $17 = (int) ($15 * 155.0F + 100.0F);
      int $7 = (int) ($15 * $15 * 255.0F);
      int $8 = (int) ($15 * $15 * $15 * $15 * $15 * $15 * $15 * $15 * $15 * $15 * 255.0F);
      short $9 = 255;

      if ($15 < 0.5F) {
        $9 = 0;
      }

      if (Minecraft.getMinecraft().gameSettings.anaglyph) {
        int $10 = ($17 * 30 + $7 * 59 + $8 * 11) / 100;
        int $11 = ($17 * 30 + $7 * 70) / 100;
        int $12 = ($17 * 30 + $8 * 70) / 100;
        $17 = $10;
        $7 = $11;
        $8 = $12;
      }

      this.imageData[$14] = TextureHelper.packRGBA($17, $7, $8, $9);
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
