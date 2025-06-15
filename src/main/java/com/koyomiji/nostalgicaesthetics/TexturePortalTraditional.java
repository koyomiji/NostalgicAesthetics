package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.MathHelper;

import java.util.Random;

/*
 * This code has been ported from Minecraft 1.4.7.
 * The copyright of the original code belongs to Mojang AB.
 */
public class TexturePortalTraditional extends TextureAtlasSprite {
  private int portalTickCounter = 0;
  private final byte[][] portalTextureData = new byte[32][1024];
  public int[] imageData = new int[256];

  public TexturePortalTraditional(String p_i1282_1_) {
    super(p_i1282_1_);
    Random $1 = new Random(100L);

    for (int $2 = 0; $2 < 32; $2++) {
      for (int $3 = 0; $3 < 16; $3++) {
        for (int $4 = 0; $4 < 16; $4++) {
          float $5 = 0.0F;

          for (int $6 = 0; $6 < 2; $6++) {
            float $7 = (float) ($6 * 16) * 0.5F;
            float $8 = (float) ($6 * 16) * 0.5F;
            float $9 = ((float) $3 - $7) / 16.0F * 2.0F;
            float $10 = ((float) $4 - $8) / 16.0F * 2.0F;
            if ($9 < -1.0F) {
              $9 += 2.0F;
            }

            if ($9 >= 1.0F) {
              $9 -= 2.0F;
            }

            if ($10 < -1.0F) {
              $10 += 2.0F;
            }

            if ($10 >= 1.0F) {
              $10 -= 2.0F;
            }

            float $11 = $9 * $9 + $10 * $10;
            float $12 = (float) Math.atan2($10, $9)
                    + ((float) $2 / 32.0F * (float) Math.PI * 2.0F - $11 * 10.0F + (float) ($6 * 2)) * (float) ($6 * 2 - 1);
            $12 = (MathHelper.sin($12) + 1.0F) / 2.0F;
            $12 /= $11 + 1.0F;
            $5 += $12 * 0.5F;
          }

          $5 += $1.nextFloat() * 0.1F;
          int $14 = (int) ($5 * 100.0F + 155.0F);
          int $15 = (int) ($5 * $5 * 200.0F + 55.0F);
          int $16 = (int) ($5 * $5 * $5 * $5 * 255.0F);
          int $17 = (int) ($5 * 100.0F + 155.0F);
          int $18 = $4 * 16 + $3;
          this.portalTextureData[$2][$18 * 4] = (byte) $15;
          this.portalTextureData[$2][$18 * 4 + 1] = (byte) $16;
          this.portalTextureData[$2][$18 * 4 + 2] = (byte) $14;
          this.portalTextureData[$2][$18 * 4 + 3] = (byte) $17;
        }
      }
    }
  }

  @Override
  public void updateAnimation() {
    this.portalTickCounter++;
    byte[] $1 = this.portalTextureData[this.portalTickCounter & 31];

    for (int $2 = 0; $2 < 256; $2++) {
      int $3 = $1[$2 * 4] & 255;
      int $4 = $1[$2 * 4 + 1] & 255;
      int $5 = $1[$2 * 4 + 2] & 255;
      int $6 = $1[$2 * 4 + 3] & 255;

      if (Minecraft.getMinecraft().gameSettings.anaglyph) {
        int $7 = ($3 * 30 + $4 * 59 + $5 * 11) / 100;
        int $8 = ($3 * 30 + $4 * 70) / 100;
        int $9 = ($3 * 30 + $5 * 70) / 100;
        $3 = $7;
        $4 = $8;
        $5 = $9;
      }

      imageData[$2] = TextureHelper.packRGBA($3, $4, $5, $6);
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
