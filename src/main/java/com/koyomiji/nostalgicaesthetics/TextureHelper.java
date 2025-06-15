package com.koyomiji.nostalgicaesthetics;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TextureHelper {
  public static BufferedImage readImage(ResourceLocation rl) {
    try (InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(rl).getInputStream()) {
      return ImageIO.read(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static int packRGBA(int r, int g, int b, int a) {
    return a << 24 | r << 16 | g << 8 | b;
  }
}
