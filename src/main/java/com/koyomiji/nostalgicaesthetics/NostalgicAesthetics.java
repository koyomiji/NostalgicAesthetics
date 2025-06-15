package com.koyomiji.nostalgicaesthetics;

import com.koyomiji.nostalgicaesthetics.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = NostalgicAesthetics.MODID, version = NostalgicAesthetics.VERSION)
public class NostalgicAesthetics {
  public static final String MODID = Tags.MODID;
  public static final String VERSION = Tags.VERSION;
  public static Logger logger;
  public static File sourceFile;

  @SidedProxy(
          clientSide = "com.koyomiji.nostalgicaesthetics.proxy.ClientProxy",
          serverSide = "com.koyomiji.nostalgicaesthetics.proxy.ServerProxy"
  )
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    logger = event.getModLog();
    sourceFile = event.getSourceFile();
    proxy.preInit(event);
  }
}
