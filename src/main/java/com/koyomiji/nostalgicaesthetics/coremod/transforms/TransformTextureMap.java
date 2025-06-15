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
import org.objectweb.asm.tree.LabelNode;

@IClassNodeTransformer.Target("net.minecraft.client.renderer.texture.TextureMap")
public class TransformTextureMap implements IClassNodeTransformer {
  private MemberSymbol registerIcon = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("a", "(Ljava/lang/String;)Lrf;")
          : new MemberSymbol("registerIcon", "(Ljava/lang/String;)Lnet/minecraft/util/IIcon;");
  private String TextureCompass = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? "bqm"
          : "net/minecraft/client/renderer/texture/TextureCompass";
  private String TextureClock = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? "bql"
          : "net/minecraft/client/renderer/texture/TextureClock";
  private String TextureAtlasSprite = NostalgicAestheticsCorePlugin.runtimeDeobfuscationEnabled
          ? "bqd"
          : "net/minecraft/client/renderer/texture/TextureAtlasSprite";

  LabelNode l0 = new LabelNode();
  LabelNode l1 = new LabelNode();
  LabelNode l2 = new LabelNode();
  LabelNode l3 = new LabelNode();
  LabelNode l4 = new LabelNode();
  LabelNode l5 = new LabelNode();
  LabelNode l6 = new LabelNode();
  LabelNode l7 = new LabelNode();

  @Override
  public ClassNode transform(ClassNode classNode) {
    return ClassQuery.of(classNode)
            .selectMethod(registerIcon.name, registerIcon.desc)
            .selectCodeFragments(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.new_(TextureCompass))
                    )
            )
            .replaceWith(
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureCompassTraditional")
            )
            .done()
            .selectCodeFragments(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.invokespecial(TextureCompass, "<init>", "(Ljava/lang/String;)V", false))
                    )
            )
            .replaceWith(
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureCompassTraditional", "<init>", "(Ljava/lang/String;)V", false)
            )
            .done()
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.new_(TextureClock))
                    )
            )
            .replaceWith(
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureClockTraditional")
            )
            .done()
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.invokespecial(TextureClock, "<init>", "(Ljava/lang/String;)V", false))
                    )
            )
            .replaceWith(
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureClockTraditional", "<init>", "(Ljava/lang/String;)V", false)
            )
            .done()
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.invokespecial(TextureAtlasSprite, "<init>", "(Ljava/lang/String;)V", false)),
                            Regexes.star(Regexes.any()),
                            Regexes.bind(0, Regexes.concatenate(
                                    CodeRegexes.stencil(InsnStencils.new_(TextureAtlasSprite)),
                                    CodeRegexes.stencil(InsnStencils.dup()),
                                    CodeRegexes.stencil(InsnStencils.aload(1)),
                                    CodeRegexes.stencil(InsnStencils.invokespecial(TextureAtlasSprite, "<init>", "(Ljava/lang/String;)V", false)),
                                    CodeRegexes.stencil(InsnStencils.astore(2)),
                                    CodeRegexes.stencil(InsnStencils.label(Stencils.bind(1)))
                            ))
                    )
            )
            .selectBound(0)
            .replaceWith(
                    InsnStencils.ldc("water_still"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifeq(l0),
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureWaterTraditional"),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureWaterTraditional", "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(l0),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.ldcInsn("water_flow"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifeq(l1),
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureWaterFlowTraditional"),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureWaterFlowTraditional", "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(l1),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.ldcInsn("lava_still"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifeq(l2),
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureLavaTraditional"),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureLavaTraditional", "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(l2),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.ldcInsn("lava_flow"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifeq(l3),
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureLavaFlowTraditional"),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureLavaFlowTraditional", "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(l3),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.ldcInsn("fire_layer_0"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifne(l4),
                    InsnStencils.ldcInsn("fire_layer_1"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifeq(l5),

                    InsnStencils.label(l4),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TextureFireTraditional"),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TextureFireTraditional", "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(l5),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.ldcInsn("portal"),
                    InsnStencils.aload(1),
                    InsnStencils.invokevirtual("java/lang/String", "equals", "(Ljava/lang/Object;)Z", false),
                    InsnStencils.ifeq(l6),
                    InsnStencils.new_("com/koyomiji/nostalgicaesthetics/TexturePortalTraditional"),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial("com/koyomiji/nostalgicaesthetics/TexturePortalTraditional", "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),
                    InsnStencils.goto_(Stencils.bound(1)),

                    InsnStencils.label(l6),
                    InsnStencils.frame(Stencils.const_(new FrameNode(Opcodes.F_NEW, 0, new Object[0], 0, new Object[0]))),
                    InsnStencils.new_(TextureAtlasSprite),
                    InsnStencils.dup(),
                    InsnStencils.aload(1),
                    InsnStencils.invokespecial(TextureAtlasSprite, "<init>", "(Ljava/lang/String;)V", false),
                    InsnStencils.astore(2),

                    InsnStencils.label(Stencils.bound(1))
            )
            .done()
            .done()
            .done()
            .done();
  }
}
