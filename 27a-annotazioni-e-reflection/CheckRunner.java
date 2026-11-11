import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
public class CheckRunner {
    public static void main(String[] args) throws IllegalAccessException {
        Method[] methods = CartChecks.class.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(Method::getName));
        CartChecks checks = new CartChecks();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Check.class)) {
                continue;
            }
            String name = method.getName();
            Check check = method.getAnnotation(Check.class);
            if (!check.enabled()) {
                System.out.println("SKIP " + name);
                continue;
            }
            try {
                method.invoke(checks);
                System.out.println("PASS " + name);
            } catch (InvocationTargetException e) {
                String reason = e.getCause().getMessage();
                System.out.println("FAIL " + name + ": " + reason);
            }
        }
    }
}
