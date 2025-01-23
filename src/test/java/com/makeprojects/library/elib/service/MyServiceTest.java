package com.makeprojects.library.elib.service;

import com.makeprojects.library.elib.ELibraryApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ELibraryApplication.class)
public class MyServiceTest {

    private MyService myService;

    @Autowired
    public MyServiceTest(MyService myService) {
        this.myService = myService;
    }

    @Test
    void divideTwoNumbers_whenInputsAreValid() {
        int a = 20;
        int b = 5;

        double result = this.myService.divideTwoNumbers(a, b);

        Assertions.assertEquals(4.0, result);
    }
}
