package com.hepsi.emlak.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class UnitTestChecker {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String packageName = "com.hepsiemlak";

        List<Class<?>> classes = getClasses(packageName);

        for (Class<?> clazz : classes) {
            if (!hasUnitTest(clazz)) {
                System.out.println(clazz.getName() + " sınıfındaki aşağıda listelenmiş metodların unit testleri eksik.");
            }

            List<Method> methodsWithoutUnitTest = getMethodsWithoutUnitTest(clazz);
            for (Method method : methodsWithoutUnitTest) {
                System.out.println("  -> " + method.getName() + " metodu unit test içermemektedir.");
            }
        }
    }

    public static List<Class<?>> getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    public static List<Method> getMethodsWithoutUnitTest(Class<?> clazz) {
        List<Method> methodsWithoutUnitTest = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (!isUnitTest(method)) {
                methodsWithoutUnitTest.add(method);
            }
        }

        return methodsWithoutUnitTest;
    }

    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }

        return classes;
    }

    public static boolean hasUnitTest(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (isUnitTest(method)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isUnitTest(Method method) {

        return method.isAnnotationPresent(org.junit.Test.class);
    }
}