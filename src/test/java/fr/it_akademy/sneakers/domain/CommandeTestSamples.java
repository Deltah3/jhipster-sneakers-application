package fr.it_akademy.sneakers.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CommandeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Commande getCommandeSample1() {
        return new Commande().id(1L).quantite(1L);
    }

    public static Commande getCommandeSample2() {
        return new Commande().id(2L).quantite(2L);
    }

    public static Commande getCommandeRandomSampleGenerator() {
        return new Commande().id(longCount.incrementAndGet()).quantite(longCount.incrementAndGet());
    }
}
