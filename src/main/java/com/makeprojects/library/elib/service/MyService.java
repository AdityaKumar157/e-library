package com.makeprojects.library.elib.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public double divideTwoNumbers(int a, int b) {
        return (double) a/b;
    }
}
