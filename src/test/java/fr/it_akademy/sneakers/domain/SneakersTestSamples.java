package fr.it_akademy.sneakers.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SneakersTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sneakers getSneakersSample1() {
        return new Sneakers().id(1L).nom("nom1").couleur("couleur1").stock(1L);
    }

    public static Sneakers getSneakersSample2() {
        return new Sneakers().id(2L).nom("nom2").couleur("couleur2").stock(2L);
    }

    public static Sneakers getSneakersRandomSampleGenerator() {
        return new Sneakers()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .couleur(UUID.randomUUID().toString())
            .stock(longCount.incrementAndGet());
    }
}
