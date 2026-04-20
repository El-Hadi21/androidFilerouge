package com.example.tpprojet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Screen1Fragment extends Fragment {

    private static final String TAG = "Screen1Fragment";
    private Notifiable notifiable;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable) {
            notifiable = (Notifiable) requireActivity();
        } else {
            throw new AssertionError("Classe "
                    + requireActivity().getClass().getName()
                    + " ne met pas en œuvre Notifiable.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen1, container, false);

        // Bouton GO → notifie l'activité (onClick fait un Log dans ControlActivity)
        Button btnGo = view.findViewById(R.id.btnGo);
        btnGo.setOnClickListener(v -> {
            Log.d(TAG, "Bouton GO cliqué dans Screen1Fragment");
            if (notifiable != null) {
                notifiable.onClick(1);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        notifiable = null;
    }
}

