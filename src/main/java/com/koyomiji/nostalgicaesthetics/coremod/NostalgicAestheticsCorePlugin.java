package com.koyomiji.nostalgicaesthetics.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"com.koyomiji.nostalgicaesthetics", "com.koyomiji.asmine"})
public class NostalgicAestheticsCorePlugin implements IFMLLoadingPlugin {
  public static File coremodLocation;
  public static File mcLocation;
  public static ArrayList<String> coremodList;
  public static Boolean runtimeDeobfuscationEnabled;
  public static Logger logger = LogManager.getLogger("NostalgicAesthetics");

  @Override
  public String[] getASMTransformerClass() {
    return new String[]{"com.koyomiji.nostalgicaesthetics.coremod.NostalgicAestheticsClassTransformer"};
  }

  @Override
  public String getModContainerClass() {
    return null;
  }

  @Override
  public String getSetupClass() {
    return "com.koyomiji.nostalgicaesthetics.coremod.NostalgicAestheticsSetup";
  }

  @Override
  public void injectData(Map<String, Object> data) {
    if (data.containsKey("coremodLocation")) {
      coremodLocation = (File) data.get("coremodLocation");
    }

    if (data.containsKey("mcLocation")) {
      mcLocation = (File) data.get("mcLocation");
    }

    if (data.containsKey("coremodList")) {
      coremodList = (ArrayList<String>) data.get("coremodList");
    }

    if (data.containsKey("runtimeDeobfuscationEnabled")) {
      runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }
  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }
}
