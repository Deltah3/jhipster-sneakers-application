package fr.it_akademy.sneakers.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Details getDetailsSample1() {
        return new Details().id(1L).description("description1").reference("reference1");
    }

    public static Details getDetailsSample2() {
        return new Details().id(2L).description("description2").reference("reference2");
    }

    public static Details getDetailsRandomSampleGenerator() {
        return new Details()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .reference(UUID.randomUUID().toString());
    }
}
