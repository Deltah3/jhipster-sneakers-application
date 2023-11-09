package fr.it_akademy.sneakers.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SneakersTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sneakers getSneakersSample1() {
        return new Sneakers().id(1L).stock(1L).nom("nom1").taille(1L).couleur("couleur1");
    }

    public static Sneakers getSneakersSample2() {
        return new Sneakers().id(2L).stock(2L).nom("nom2").taille(2L).couleur("couleur2");
    }

    public static Sneakers getSneakersRandomSampleGenerator() {
        return new Sneakers()
            .id(longCount.incrementAndGet())
            .stock(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .taille(longCount.incrementAndGet())
            .couleur(UUID.randomUUID().toString());
    }
}
