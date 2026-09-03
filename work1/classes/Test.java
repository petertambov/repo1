package work1.classes;

public class Test {
    private TestResult result;
    private String name;
    private String exception;

    void setResult(TestResult result) {
        this.result = result;
    }

    void setName(String name) {
        this.name = name;
    }

    void setException(String exception) {
         this.exception = exception;
    }

    public TestResult getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public String getException() {
        return exception;
    }

    public String toString() {
       return String.format("name = '%s', result = %s%s",
               name, result, (exception != null) ? ", exception = " + exception : "");
    }
}
