package com.soen487.rest.project.repository.core.service;

import org.springframework.stereotype.Service;

@Service
public class CalculateService {
    private static int num;

    public CalculateService() {
        this.num = 0;
    }

    public int plus(int a, int b){
        return a + b;
    }

    public int plusplus(int a) {
        this.num += a;
        this.num++;
        return this.num;
    }

    public int minus(int a, int b){
        return a - b;
    }
}
