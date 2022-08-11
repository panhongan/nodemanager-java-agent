package com.github.panhongan.bigdata.extension.nodemanager;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class NodeManagerTransformer implements ClassFileTransformer {

    public static final String CLASS_BY_DOT = "org.apache.hadoop.fs.FileUtil";

    public static final String MAPPER_CLASS_BY_SLASH = CLASS_BY_DOT.replace('.', '/');

    public static final String TARGET_METHOD = "execCommand";


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            if (className.equals(MAPPER_CLASS_BY_SLASH)) {
                ClassPool cp = ClassPool.getDefault();
                CtClass clazz = cp.get(CLASS_BY_DOT);
                CtMethod ctMethod = clazz.getDeclaredMethod(TARGET_METHOD);
                ctMethod.insertAfter("System.out.println(\"file_path: \" + $1.getAbsolutePath());" +
                        "System.out.println(\"cmd: \" + java.util.Arrays.toString($2))");
                return clazz.toBytecode();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return classfileBuffer;
    }
}
