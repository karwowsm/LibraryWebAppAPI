package com.testowanie_oprogramowania.library.benchmark;

import com.testowanie_oprogramowania.library.LibraryApplication;
import org.junit.runner.RunWith;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class BenchmarkBase {
    public static void main(String[] args) throws RunnerException, IOException {

        ResultFormatType resultsFileOutputType = ResultFormatType.LATEX;

        Options opt = new OptionsBuilder()
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(1)
                .threads(1)
                .shouldDoGC(true)
                .resultFormat(resultsFileOutputType)
                .result(buildResultsFileName("jmh-", resultsFileOutputType))
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
    }

    private static String buildResultsFileName(String resultFilePrefix, ResultFormatType resultType) {

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm-dd-yyyy-hh-mm-ss");

        String suffix;
        switch (resultType) {
            case CSV:
                suffix = ".csv";
                break;
            case SCSV:
                suffix = ".scsv";
                break;
            case LATEX:
                suffix = ".tex";
                break;
            case JSON:
            default:
                suffix = ".json";
                break;
        }
        return String.format("target/%s%s%s", resultFilePrefix, date.format(formatter), suffix);
    }


}
