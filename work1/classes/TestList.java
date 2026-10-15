package work1.classes;

import work1.annotations.*;

public class TestList {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("Start all testing");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("Finish all testing");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Start regular test");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Finish regular test");
    }

    @Check(priority = 10, value = "Success result !!!")
    public void successTest() {
        System.out.println("execute: Success test");
    }

    @Check(priority = 1)
    public void failedTest() throws Exception {
        System.out.println("execute: Fail test");
        throw new TestAssertionError("Wrong result");
    }

    @Check(priority = 2)
    public void errorTest() throws Exception {
        System.out.println("execute: Error test");
        throw new MyError("AAAAAAA");
    }
    
    @Check(priority = 1, value = "Skipped test !!!")
    @Disabled
    public void skippedTest() {
        System.out.println("execute: Skipped test");
    }
        
    @Check(value = "Order test !!!")
    @Order(1)
    public void orderTest() {
        System.out.println("execute: Order test");
    }
}
