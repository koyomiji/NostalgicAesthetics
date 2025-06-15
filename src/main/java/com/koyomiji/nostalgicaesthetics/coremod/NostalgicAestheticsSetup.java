package com.koyomiji.nostalgicaesthetics.coremod;

import com.koyomiji.nostalgicaesthetics.setup.AssetFetcher;
import com.koyomiji.nostalgicaesthetics.setup.AssetIdentifier;
import cpw.mods.fml.relauncher.IFMLCallHook;
import net.minecraft.launchwrapper.LaunchClassLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class NostalgicAestheticsSetup implements IFMLCallHook {
  static boolean runtimeDeobfuscationEnabled;
  static File mcLocation;
  static LaunchClassLoader classLoader;
  static File coremodLocation;
  static String deobfuscationFileName;

  static AssetIdentifier client147 = new AssetIdentifier(
          "https://piston-data.mojang.com/v1/objects/53ed4b9d5c358ecfff2d8b846b4427b888287028/client.jar",
          "53ed4b9d5c358ecfff2d8b846b4427b888287028",
          5005735
  );

  @Override
  public void injectData(Map<String, Object> data) {
    if (data.containsKey("runtimeDeobfuscationEnabled")) {
      runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    if (data.containsKey("mcLocation")) {
      mcLocation = (File) data.get("mcLocation");
    }

    if (data.containsKey("classLoader")) {
      classLoader = (LaunchClassLoader) data.get("classLoader");
    }

    if (data.containsKey("coremodLocation")) {
      coremodLocation = (File) data.get("coremodLocation");
    }

    if (data.containsKey("deobfuscationFileName")) {
      deobfuscationFileName = (String) data.get("deobfuscationFileName");
    }
  }

  private String getAssetsJarName() {
    return coremodLocation.getName().replace(".jar", "-assets.jar");
  }

  @Override
  public Void call() throws Exception {
    File assetsJar = new File(mcLocation, "mods" + File.separator + getAssetsJarName());

    if (!assetsJar.exists()) {
      AssetFetcher fetcher = new AssetFetcher(
              Files.createTempDirectory("nostalgicaesthetics")
      );

      try (JarOutputStream zos = new JarOutputStream(new FileOutputStream(assetsJar))) {
        try (JarFile client164Jar = new JarFile(fetcher.fetchAndGetPath(client147).toFile())) {
          try (InputStream is = client164Jar.getInputStream(client164Jar.getEntry("gui/items.png"))) {
            BufferedImage image = ImageIO.read(is);

            {
              BufferedImage clock = image.getSubimage(6 * 16, 4 * 16, 16, 16);

              zos.putNextEntry(new ZipEntry("assets/nostalgicaesthetics/textures/items/clock.png"));
              ImageIO.write(clock, "PNG", zos);
              zos.closeEntry();
            }

            {
              BufferedImage compass = image.getSubimage(6 * 16, 3 * 16, 16, 16);

              zos.putNextEntry(new ZipEntry("assets/nostalgicaesthetics/textures/items/compass.png"));
              ImageIO.write(compass, "PNG", zos);
              zos.closeEntry();
            }
          }

          try (InputStream is = client164Jar.getInputStream(client164Jar.getEntry("terrain.png"))) {
            BufferedImage image = ImageIO.read(is);

            {
              BufferedImage clock = image.getSubimage(12 * 16, 0, 16, 16);

              zos.putNextEntry(new ZipEntry("assets/minecraft/textures/blocks/flower_rose.png"));
              ImageIO.write(clock, "PNG", zos);
              zos.closeEntry();
            }
          }

          try (InputStream is = client164Jar.getInputStream(client164Jar.getEntry("misc/dial.png"))) {
            BufferedImage image = ImageIO.read(is);
            zos.putNextEntry(new ZipEntry("assets/nostalgicaesthetics/textures/misc/dial.png"));
            ImageIO.write(image, "PNG", zos);
            zos.closeEntry();
          }
        }
      } catch (IOException e) {
        throw new RuntimeException("Failed to generate assets", e);
      }
    }

    return null;
  }
}
