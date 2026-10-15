package work1.classes;


import work1.annotations.Check;
import work1.annotations.Order;

import java.lang.reflect.*;

public class MyMethod {

    public final Method method;
    private final String name;
    private final int priority;
    private final int order;

    public MyMethod(Method method) {
        this.method = method;
        Check checkAnn = method.getAnnotation(Check.class);
        name = (checkAnn == null || checkAnn.value().isEmpty()) ? method.getName() : checkAnn.value();
        priority = (checkAnn == null) ? 5 : checkAnn.priority();
        Order orderAnn = method.getAnnotation(Order.class);
        order = (orderAnn == null) ? 5 : orderAnn.value();
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getOrder() {
        return order;
    }
}
