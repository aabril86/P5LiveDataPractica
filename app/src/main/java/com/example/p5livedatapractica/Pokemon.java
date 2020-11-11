package com.example.p5livedatapractica;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Pokemon {

    interface EntrenadorListener {
        void cuandoEvolucione(String orden);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> entrenando;

    void iniciarEvolucion(final EntrenadorListener entrenadorListener) {
        if (entrenando == null || entrenando.isCancelled()) {
            entrenando = scheduler.scheduleAtFixedRate(new Runnable() {
                int evolucion;

                @Override
                public void run() {

                    evolucion++;
                    if (evolucion==5) evolucion=1;

                    entrenadorListener.cuandoEvolucione("EVOLUCION" + evolucion);
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararEntrenamiento() {
        if (entrenando != null) {
            entrenando.cancel(true);
        }


    }
    LiveData<String> ordenLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarEvolucion(new EntrenadorListener() {
                @Override
                public void cuandoEvolucione(String evolucion) {
                    postValue(evolucion);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararEntrenamiento();
        }
    };
}
