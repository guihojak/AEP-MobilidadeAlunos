package br.unicesumar.caronas.view;

import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Inicializa o toolkit JavaFX apenas uma vez, de forma thread-safe.
 */
public final class JavaFXInitializer {
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private JavaFXInitializer() {}

    public static void init() {
        if (initialized.get()) return;

        CountDownLatch latch = new CountDownLatch(1);

        Thread t = new Thread(() -> {
            try {
                // Se já inicializado, Platform.startup lançará IllegalStateException; capturamos abaixo.
                Platform.startup(() -> {
                    // No-op: apenas inicializa o toolkit
                });
            } catch (IllegalStateException ex) {
                // Já inicializado — ignora
            } finally {
                initialized.set(true);
                latch.countDown();
            }
        }, "JavaFX-Initer");
        t.setDaemon(true);
        t.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
