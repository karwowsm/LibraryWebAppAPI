package com.testowanie_oprogramowania.library.benchmark;

import org.openjdk.jmh.annotations.Benchmark;

public class ExampleBenchmark {
    @Benchmark
    public static String test() {
       return "test";
    }
}
