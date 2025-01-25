package com.makeprojects.library.elib.core.service.implementation;

import com.makeprojects.library.elib.core.service.definition.ArithmeticService;
import org.springframework.stereotype.Service;

@Service
public class ArithmeticServiceImpl implements ArithmeticService {

    @Override
    public double divideTwoNumbers(int a, int b) {
        return (double) a/b;
    }
}
