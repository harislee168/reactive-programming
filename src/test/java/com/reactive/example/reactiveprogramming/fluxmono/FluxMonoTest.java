package com.reactive.example.reactiveprogramming.fluxmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoTest {

    @Test
    public void testMonoSuccess() {
        Mono <String> monoStr = Mono.just("Test String").log();
        monoStr.subscribe((str) -> System.out.println("message is: " + str));
    }

    @Test
    public void testMonoFail() {
        Mono <?> monoStr = Mono.just("Test String")
                .then(Mono.error(new Throwable("Error happened")))
                .log();
        monoStr.subscribe((str) -> System.out.println("message is: " + str),
                (error) -> System.out.println("error is: " + error));
    }

    @Test
    public void testFluxSuccess() {
        Flux <String> fluxStr = Flux.just("Andy", "Band", "Corina", "David")
                .concatWithValues("Evelyn")
                .log();
    }
}
