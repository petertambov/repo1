package work1.classes;

import work1.annotations.*;

import java.lang.reflect.*;
import java.util.*;


public class TestRunner {


    public static void execMethods(Object instance, List<MyMethod> listMyMethod) throws Exception {
        for (MyMethod myMethod : listMyMethod)
        try {
            myMethod.method.invoke(instance);
        } catch (Exception e) {
            throw new BadTestClassError(myMethod, e.getMessage());
        }
    }

    public static Map<TestResult, List<Test>> runTests(Class<?> c) throws Exception{
        Map<TestResult, List<Test>> resultMap = new HashMap<TestResult, List<Test>>();

        for (TestResult tr : TestResult.values()) {
            resultMap.put(tr, new ArrayList<Test>());
        }

        try {

            Object instance = null;
            try {
                instance = c.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new BadTestClassError("can't create object: " + e.getMessage());
            }

            List<MyMethod> checkMethods = new ArrayList<MyMethod>();
            List<MyMethod> beforeSuiteMethods = new ArrayList<MyMethod>();
            List<MyMethod> afterSuiteMethods = new ArrayList<MyMethod>();
            List<MyMethod> beforeEachMethods = new ArrayList<MyMethod>();
            List<MyMethod> afterEachMethods = new ArrayList<MyMethod>();

            for (Method method : c.getDeclaredMethods()) {
                method.setAccessible(true);
                MyMethod myMethod = new MyMethod(method);

                if (Modifier.isStatic(method.getModifiers()) &&
                    (method.isAnnotationPresent(BeforeEach.class) ||
                            method.isAnnotationPresent(AfterEach.class) ||
                            method.isAnnotationPresent(Check.class)) ||
                        !Modifier.isStatic(method.getModifiers()) &&
                 (method.isAnnotationPresent(BeforeSuite.class) ||
                 method.isAnnotationPresent(AfterSuite.class)))
                 throw new BadTestClassError(myMethod,"wrong annotation");

                if (method.isAnnotationPresent(Check.class))  checkMethods.add(myMethod);
                if (method.isAnnotationPresent(BeforeSuite.class))  beforeSuiteMethods.add(myMethod);
                if (method.isAnnotationPresent(AfterSuite.class))  afterSuiteMethods.add(myMethod);
                if (method.isAnnotationPresent(BeforeEach.class))  beforeEachMethods.add(myMethod);
                if (method.isAnnotationPresent(AfterEach.class))  afterEachMethods.add(myMethod);

            }
            checkMethods.sort(Comparator.comparingInt(MyMethod::getPriority).reversed().
                    thenComparingInt(MyMethod::getOrder).
                    thenComparing(MyMethod::getName));

            execMethods(instance,beforeSuiteMethods);
            for (MyMethod myMethod : checkMethods)
                try {
                    Method method = myMethod.method;
                    Test test = new Test();
                    test.setName(myMethod.getName());
                    if (method.isAnnotationPresent(Disabled.class))
                        test.setResult(TestResult.Skipped);
                    else
                    {
                        execMethods(instance,beforeEachMethods);
                        try {
                            method.invoke(instance);
                            test.setResult(TestResult.Success);
                        }
                        catch (InvocationTargetException e) {
                            Throwable cause = e.getCause();
                            if (cause instanceof TestAssertionError)
                                test.setResult(TestResult.Failed);
                            else {
                                test.setResult(TestResult.Error);
                                test.setException(cause.getMessage());
                            }
                        }
                        execMethods(instance,afterEachMethods);
                    }
                    resultMap.get(test.getResult()).add(test);
                } catch (Exception e) {
                    throw new BadTestClassError(myMethod, e.getMessage());
                }
            execMethods(instance, afterSuiteMethods);

        } catch (Exception e) {
            throw e;
        }


        return resultMap;
    }
}
