package com.koyomiji.nostalgicaesthetics.coremod;

import net.minecraft.launchwrapper.IClassTransformer;

public class NostalgicAestheticsClassTransformer implements IClassTransformer {
  private ClassTransformLoader applier;

  @Override
  public byte[] transform(String name, String transformedName, byte[] basicClass) {
    if (applier == null) {
      applier = new ClassTransformLoader(NostalgicAestheticsCorePlugin.coremodLocation, "com.koyomiji.nostalgicaesthetics.coremod.transforms");
    }

    return applier.transform(name, transformedName, basicClass);
  }
}
