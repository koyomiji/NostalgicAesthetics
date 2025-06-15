package com.koyomiji.nostalgicaesthetics.coremod.transforms;

import com.koyomiji.asmine.common.InsnStencils;
import com.koyomiji.asmine.query.ClassQuery;
import com.koyomiji.asmine.regex.compiler.Regexes;
import com.koyomiji.asmine.regex.compiler.code.CodeRegexes;
import com.koyomiji.nostalgicaesthetics.coremod.IClassNodeTransformer;
import com.koyomiji.nostalgicaesthetics.coremod.MemberSymbol;
import com.koyomiji.nostalgicaesthetics.coremod.NostalgicAestheticsCorePlugin;
import org.objectweb.asm.tree.ClassNode;

@IClassNodeTransformer.Target("net.minecraft.block.BlockReed")
public class TransformBlockReed implements IClassNodeTransformer {
  private MemberSymbol colorMultiplier = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("d", "(Lahl;III)I")
          : new MemberSymbol("colorMultiplier", "(Lnet/minecraft/world/IBlockAccess;III)I");
  private MemberSymbol getBiomeGenForCoords = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("ahl", "a", "(II)Lahu;")
          : new MemberSymbol("net/minecraft/world/IBlockAccess", "getBiomeGenForCoords", "(II)Lnet/minecraft/world/biome/BiomeGenBase;");
  private MemberSymbol getBiomeGrassColor = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("ahu", "b", "(III)I")
          : new MemberSymbol("net/minecraft/world/biome/BiomeGenBase", "getBiomeGrassColor", "(III)I");
  private MemberSymbol Block_colorMultiplier = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("aji", "d", "(Lahl;III)I")
          : new MemberSymbol("net/minecraft/block/Block", "colorMultiplier", "(Lnet/minecraft/world/IBlockAccess;III)I");

  @Override
  public ClassNode transform(ClassNode classNode) {
    return ClassQuery.of(classNode)
            .selectMethod(colorMultiplier.name, colorMultiplier.desc)
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.aload(1)),
                            CodeRegexes.stencil(InsnStencils.iload(2)),
                            CodeRegexes.stencil(InsnStencils.iload(4)),
                            CodeRegexes.stencil(InsnStencils.invokeinterface(getBiomeGenForCoords.owner, getBiomeGenForCoords.name, getBiomeGenForCoords.desc, true)),
                            CodeRegexes.stencil(InsnStencils.iload(2)),
                            CodeRegexes.stencil(InsnStencils.iload(3)),
                            CodeRegexes.stencil(InsnStencils.iload(4)),
                            CodeRegexes.stencil(InsnStencils.invokevirtual(getBiomeGrassColor.owner, getBiomeGrassColor.name, getBiomeGrassColor.desc, false)),
                            CodeRegexes.stencil(InsnStencils.ireturn())
                    )
            )
            .replaceWith(
                    InsnStencils.aload(0),
                    InsnStencils.aload(1),
                    InsnStencils.iload(2),
                    InsnStencils.iload(3),
                    InsnStencils.iload(4),
                    InsnStencils.invokespecial(
                            Block_colorMultiplier.owner, Block_colorMultiplier.name, Block_colorMultiplier.desc, false
                    ),
                    InsnStencils.ireturn()
            )
            .done()
            .done()
            .done();
  }
}
