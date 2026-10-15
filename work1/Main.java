package work1;

import java.util.List;
import java.util.Map;

import work1.classes.*;

public class Main {
    public static void main(String[] args) {
        try {
            Map<TestResult, List<Test>> results = TestRunner.runTests(TestList.class);

            System.out.println("-------------------------------------");
            for (TestResult testResult : TestResult.values()) {
                List<Test> testList = results.get(testResult);
                if (!testList.isEmpty()) {
                    System.out.println(testResult + ": " + testList.size());
                    for (Test test : testList)
                        System.out.println("  - " + test.toString());
                    }
                }
                System.out.println("-------------------------------------");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
