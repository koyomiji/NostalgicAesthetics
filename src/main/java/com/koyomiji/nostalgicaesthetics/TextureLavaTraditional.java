package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.MathHelper;

/*
 * This code has been ported from Minecraft 1.4.7.
 * The copyright of the original code belongs to Mojang AB.
 */
public class TextureLavaTraditional extends TextureAtlasSprite {
  protected float[] field_76876_g = new float[256];
  protected float[] field_76878_h = new float[256];
  protected float[] field_76879_i = new float[256];
  protected float[] field_76877_j = new float[256];
  public int[] imageData = new int[256];

  public TextureLavaTraditional(String p_i1282_1_) {
    super(p_i1282_1_);
  }

  @Override
  public void updateAnimation() {
    this.tickCounter++;
    for (int $1 = 0; $1 < 16; $1++) {
      for (int $2 = 0; $2 < 16; $2++) {
        float $3 = 0.0F;
        int $4 = (int) (MathHelper.sin((float) $2 * (float) Math.PI * 2.0F / 16.0F) * 1.2F);
        int $5 = (int) (MathHelper.sin((float) $1 * (float) Math.PI * 2.0F / 16.0F) * 1.2F);

        for (int $6 = $1 - 1; $6 <= $1 + 1; $6++) {
          for (int $7 = $2 - 1; $7 <= $2 + 1; $7++) {
            int $8 = $6 + $4 & 15;
            int $9 = $7 + $5 & 15;
            $3 += this.field_76876_g[$8 + $9 * 16];
          }
        }

        this.field_76878_h[$1 + $2 * 16] = $3 / 10.0F + (
                this.field_76879_i[($1 & 15) + ($2 & 15) * 16]
                        + this.field_76879_i[($1 + 1 & 15) + ($2 & 15) * 16]
                        + this.field_76879_i[($1 + 1 & 15) + ($2 + 1 & 15) * 16]
                        + this.field_76879_i[($1 & 15) + ($2 + 1 & 15) * 16]
        ) / 4.0F * 0.8F;
        this.field_76879_i[$1 + $2 * 16] = this.field_76879_i[$1 + $2 * 16] + this.field_76877_j[$1 + $2 * 16] * 0.01F;
        if (this.field_76879_i[$1 + $2 * 16] < 0.0F) {
          this.field_76879_i[$1 + $2 * 16] = 0.0F;
        }

        this.field_76877_j[$1 + $2 * 16] = this.field_76877_j[$1 + $2 * 16] - 0.06F;
        if (Math.random() < 0.005) {
          this.field_76877_j[$1 + $2 * 16] = 1.5F;
        }
      }
    }

    float[] $11 = this.field_76878_h;
    this.field_76878_h = this.field_76876_g;
    this.field_76876_g = $11;

    for (int $12 = 0; $12 < 256; $12++) {
      float $13 = this.field_76876_g[$12] * 2.0F;
      if ($13 > 1.0F) {
        $13 = 1.0F;
      }

      if ($13 < 0.0F) {
        $13 = 0.0F;
      }

      int $14 = (int) ($13 * 100.0F + 155.0F);
      int $15 = (int) ($13 * $13 * 255.0F);
      int $16 = (int) ($13 * $13 * $13 * $13 * 128.0F);

      if (Minecraft.getMinecraft().gameSettings.anaglyph) {
        int var17 = ($14 * 30 + $15 * 59 + $16 * 11) / 100;
        int var18 = ($14 * 30 + $15 * 70) / 100;
        int var10 = ($14 * 30 + $16 * 70) / 100;
        $14 = var17;
        $15 = var18;
        $16 = var10;
      }

      this.imageData[$12] = TextureHelper.packRGBA($14, $15, $16, 255);
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
