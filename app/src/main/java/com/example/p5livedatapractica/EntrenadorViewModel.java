package com.example.p5livedatapractica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class EntrenadorViewModel extends AndroidViewModel {
    Pokemon pokemon;

    LiveData<Integer> imagenLiveData;

    public EntrenadorViewModel(@NonNull Application application) {
        super(application);

        pokemon = new Pokemon();

        imagenLiveData = Transformations.switchMap(pokemon.ordenLiveData, new Function<String, LiveData<Integer>>() {
            @Override
            public LiveData<Integer> apply(String evolucion) {
                int imagen;
                switch (evolucion) {
                    case "EVOLUCION1":
                    default:
                        imagen = R.drawable.bulbasaur;
                        break;
                    case "EVOLUCION2":
                        imagen = R.drawable.ivysaur;
                        break;
                    case "EVOLUCION3":
                        imagen = R.drawable.venosaur;
                        break;
                    case "EVOLUCION4":
                        imagen = R.drawable.all;
                        break;
                }

                return new MutableLiveData<>(imagen);
            }
        });
    }

    public LiveData<Integer> obtenerImagen(){
        return imagenLiveData;
    }
}