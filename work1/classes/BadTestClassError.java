package work1.classes;

public class BadTestClassError extends Exception {

    public BadTestClassError(String message) {
        super(message);
    }

    public BadTestClassError(MyMethod myMethod, String message) {
        super(String.format("Error in method '%s': %s",myMethod.getName(),message));
    }
}