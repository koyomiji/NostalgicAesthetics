package com.koyomiji.nostalgicaesthetics.coremod.transforms;

import com.koyomiji.asmine.common.InsnStencils;
import com.koyomiji.asmine.query.ClassQuery;
import com.koyomiji.asmine.regex.compiler.Regexes;
import com.koyomiji.asmine.regex.compiler.code.CodeRegexes;
import com.koyomiji.asmine.stencil.Stencils;
import com.koyomiji.nostalgicaesthetics.coremod.IClassNodeTransformer;
import com.koyomiji.nostalgicaesthetics.coremod.MemberSymbol;
import com.koyomiji.nostalgicaesthetics.coremod.NostalgicAestheticsCorePlugin;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;

@IClassNodeTransformer.Target("net.minecraft.client.renderer.EntityRenderer")
public class TransformEntityRenderer implements IClassNodeTransformer {
  private MemberSymbol setupFog = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("a", "(IF)V")
          : new MemberSymbol("setupFog", "(IF)V");
  private MemberSymbol updateFogColor = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("j", "(F)V")
          : new MemberSymbol("updateFogColor", "(F)V");
  private MemberSymbol mc = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("blt", "t", "Lbao;")
          : new MemberSymbol("net/minecraft/client/renderer/EntityRenderer", "mc", "Lnet/minecraft/client/Minecraft;");
  private MemberSymbol gameSettings = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("bao", "u", "Lbbj;")
          : new MemberSymbol("net/minecraft/client/Minecraft", "gameSettings", "Lnet/minecraft/client/settings/GameSettings;");
  private MemberSymbol renderDistanceChunks = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("bbj", "c", "I")
          : new MemberSymbol("net/minecraft/client/settings/GameSettings", "renderDistanceChunks", "I");

  @Override
  public ClassNode transform(ClassNode classNode) {
    return ClassQuery.of(classNode)
            .selectMethod(setupFog.name, setupFog.desc)
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.iload(1)),
                            CodeRegexes.stencil(InsnStencils.ifge(Stencils.bind(0))),

                            CodeRegexes.stencil(InsnStencils.sipush(2915)),
                            CodeRegexes.stencil(InsnStencils.fconst_0()),
                            CodeRegexes.stencil(InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false)),
                            CodeRegexes.stencil(InsnStencils.sipush(2916)),
                            CodeRegexes.stencil(InsnStencils.fload(6)),
                            CodeRegexes.stencil(InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false)),
                            CodeRegexes.stencil(InsnStencils.goto_(Stencils.bind(1))),

                            CodeRegexes.stencil(InsnStencils.label(Stencils.bound(0))),
                            CodeRegexes.stencil(InsnStencils.sipush(2915)),
                            CodeRegexes.stencil(InsnStencils.fload(6)),
                            CodeRegexes.stencil(InsnStencils.ldc(0.75F)),
                            CodeRegexes.stencil(InsnStencils.fmul()),
                            CodeRegexes.stencil(InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false)),
                            CodeRegexes.stencil(InsnStencils.sipush(2916)),
                            CodeRegexes.stencil(InsnStencils.fload(6)),
                            CodeRegexes.stencil(InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false)),
                            CodeRegexes.stencil(InsnStencils.label(Stencils.bound(1)))
                    )
            )
            .replaceWith(
                    InsnStencils.iload(1),
                    InsnStencils.ifge(Stencils.bound(0)),

                    InsnStencils.sipush(2915),
                    InsnStencils.fconst_0(),
                    InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false),
                    InsnStencils.sipush(2916),
                    InsnStencils.fload(6),
                    InsnStencils.ldc(0.8F),
                    InsnStencils.fmul(),
                    InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(Stencils.bound(0)),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.sipush(2915),
                    InsnStencils.fload(6),
                    InsnStencils.ldc(0.25F),
                    InsnStencils.fmul(),
                    InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false),
                    InsnStencils.sipush(2916),
                    InsnStencils.fload(6),
                    InsnStencils.invokestatic("org/lwjgl/opengl/GL11", "glFogf", "(IF)V", false),
                    InsnStencils.label(Stencils.bound(1))
            )
            .done()
            .done()
            .selectMethod(updateFogColor.name, updateFogColor.desc)
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.ldc(0.25F)),
                            CodeRegexes.stencil(InsnStencils.ldc(0.75F)),
                            CodeRegexes.stencil(InsnStencils.aload(0)),
                            CodeRegexes.stencil(InsnStencils.getfield(mc.owner, mc.name, mc.desc)),
                            CodeRegexes.stencil(InsnStencils.getfield(gameSettings.owner, gameSettings.name, gameSettings.desc)),
                            CodeRegexes.stencil(InsnStencils.getfield(renderDistanceChunks.owner, renderDistanceChunks.name, renderDistanceChunks.desc)),
                            CodeRegexes.stencil(InsnStencils.i2f()),
                            CodeRegexes.stencil(InsnStencils.fmul()),
                            CodeRegexes.stencil(InsnStencils.ldc(16.0F)),
                            CodeRegexes.stencil(InsnStencils.fdiv()),
                            CodeRegexes.stencil(InsnStencils.fadd()),
                            CodeRegexes.stencil(InsnStencils.fstore(4))
                    )
            )
            .replaceWith(
                    InsnStencils.ldc(1.0F),
                    InsnStencils.aload(0),
                    InsnStencils.getfield(mc.owner, mc.name, mc.desc),
                    InsnStencils.getfield(gameSettings.owner, gameSettings.name, gameSettings.desc),
                    InsnStencils.getfield(renderDistanceChunks.owner, renderDistanceChunks.name, renderDistanceChunks.desc),
                    InsnStencils.i2d(),
                    InsnStencils.invokestatic("java/lang/Math", "log", "(D)D", false),
                    InsnStencils.ldc(2.0D),
                    InsnStencils.invokestatic("java/lang/Math", "log", "(D)D", false),
                    InsnStencils.ddiv(),
                    InsnStencils.d2f(),
                    InsnStencils.fdiv(),
                    InsnStencils.fstore(4)
            )
            .done()
            .done()
            .done();

  }
}
