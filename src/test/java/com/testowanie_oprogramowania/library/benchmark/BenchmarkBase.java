package com.testowanie_oprogramowania.library.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BenchmarkBase {
    public static void main(String[] args) throws RunnerException, IOException {
        Options opt = new OptionsBuilder()
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();


        new Runner(opt).run();
    }
}
