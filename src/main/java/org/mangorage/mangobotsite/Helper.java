package org.mangorage.mangobotsite;

import java.lang.reflect.Method;

public final class Helper {
    public static boolean isDevMode() {

        try {
            final var clz = getLoadedClassFromModule(
                    "org.mangorage.mangobotcore",
                    "org.mangorage.entrypoint.MangoBotCore"
            );

            final var method = clz.getDeclaredMethod("isDevMode");

            return (boolean) method.invoke(Helper.class, null);
        } catch (Throwable e) {
            return false;
        }
    }


    public static Method getSimpleMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.isSynthetic() && !method.isBridge()) {
                return method;
            }
        }
        throw new RuntimeException("No simple method found in " + clazz.getName());
    }

    public static Class<?> getLoadedClassFromModule(String moduleName, String className) {
        ModuleLayer bootLayer = Helper.class.getModule().getLayer();

        return bootLayer.findModule(moduleName)
                .map(Module::getClassLoader)
                .map(loader -> {
                    try {
                        return Class.forName(className, false, loader); // `false` to avoid re-init
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("Class not found in module: " + className, e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("Module not found: " + moduleName));
    }

}
