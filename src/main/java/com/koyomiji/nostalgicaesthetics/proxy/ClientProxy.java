package com.koyomiji.nostalgicaesthetics.proxy;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.IResourcePack;

import java.io.File;
import java.util.List;

public class ClientProxy extends CommonProxy {
  private String getAssetsJarName(File modFile) {
    return modFile.getName().replace(".jar", "-assets.jar");
  }

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);

    List<IResourcePack> defaultPacks = ObfuscationReflectionHelper.getPrivateValue(
            Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "field_110449_ao"
    );

    defaultPacks.add(new FileResourcePack(new File(event.getModConfigurationDirectory().getParent(), "mods" + File.separator + getAssetsJarName(event.getSourceFile()))));
    Minecraft.getMinecraft().refreshResources();
  }
}
