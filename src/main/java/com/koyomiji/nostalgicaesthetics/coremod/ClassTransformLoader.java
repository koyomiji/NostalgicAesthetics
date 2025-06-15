package com.koyomiji.nostalgicaesthetics.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassTransformLoader implements IClassTransformer {
    private final Map<String, ArrayList<IClassNodeTransformer>> transforms = new HashMap<>();

    public ClassTransformLoader(File coremodLocation, String packageName) {
        discoverTransforms(coremodLocation, packageName);
    }

    private void loadTransform(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (IClassNodeTransformer.class.isAssignableFrom(clazz)) {
                IClassNodeTransformer transform = (IClassNodeTransformer) clazz.getDeclaredConstructor().newInstance();
                addTransform(transform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void discoverTransforms(File coremodLocation, String packageName) {
        try (JarFile jarFile = new JarFile(coremodLocation)) {
            Enumeration<JarEntry> en = jarFile.entries();

            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                String name = entry.getName();

                if (name.endsWith(".class") && name.startsWith(packageName.replace('.', '/') + "/")) {
                    String className = name.substring(0, name.length() - 6).replace('/', '.');
                    loadTransform(className);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTransform(IClassNodeTransformer transform) {
        for (Annotation annotation : transform.getClass().getDeclaredAnnotations()) {
            if (annotation instanceof IClassNodeTransformer.Target) {
                IClassNodeTransformer.Target target = (IClassNodeTransformer.Target)annotation;
                ArrayList<IClassNodeTransformer> transforms = this.transforms.getOrDefault(target.value(), null);

                if (transforms == null) {
                    transforms = new ArrayList<>();
                }

                transforms.add(transform);
                this.transforms.put(target.value(), transforms);
            }
        }
    }

    private List<IClassNodeTransformer> getTransforms(String className) {
        return transforms.getOrDefault(className, new ArrayList<>());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        List<IClassNodeTransformer> transforms = getTransforms(transformedName);

        if (transforms.size() == 0) {
            return basicClass;
        }

        try {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(basicClass);
            reader.accept(node, ClassReader.EXPAND_FRAMES);
            System.out.println("Transforming class " + transformedName + " with " + transforms.size() + " transforms");

            for (IClassNodeTransformer transform : transforms) {
                node = transform.transform(node);
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            return writer.toByteArray();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
