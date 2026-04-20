package com.example.tpprojet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";
    private static final String ARG_MENU_INDEX = "menuIndex";

    private Menuable menuable;
    private int currentActivatedIndex = 0;

    // Images NON sélectionnées (grises/normales)
    private final int[] iconsNormal = {
            R.drawable.menu0,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
    };

    // Images SÉLECTIONNÉES (vertes - suffixe _s)
    private final int[] iconsSelected = {
            R.drawable.menu0_s,
            R.drawable.menu1_s,
            R.drawable.menu2_s,
            R.drawable.menu3_s,
            R.drawable.menu4_s,
            R.drawable.menu5_s,
            R.drawable.menu6_s
    };

    private ImageButton[] buttons;

    public static MenuFragment newInstance(int menuIndex) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MENU_INDEX, menuIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Menuable) {
            menuable = (Menuable) requireActivity();
            Log.d(TAG, "Class " + requireActivity().getClass().getSimpleName() + " implements Menuable.");
        } else {
            throw new AssertionError("Classe "
                    + requireActivity().getClass().getName()
                    + " ne met pas en œuvre Menuable.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Récupère l'index initial passé en argument
        if (getArguments() != null) {
            currentActivatedIndex = getArguments().getInt(ARG_MENU_INDEX, 0);
        }

        // Références aux 7 boutons
        buttons = new ImageButton[]{
                view.findViewById(R.id.menuBtn0),
                view.findViewById(R.id.menuBtn1),
                view.findViewById(R.id.menuBtn2),
                view.findViewById(R.id.menuBtn3),
                view.findViewById(R.id.menuBtn4),
                view.findViewById(R.id.menuBtn5),
                view.findViewById(R.id.menuBtn6)
        };

        // Affiche les icônes et gère les clics
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            updateButtonIcon(i);
            buttons[i].setOnClickListener(v -> {
                currentActivatedIndex = index;
                refreshAllIcons();
                if (menuable != null) {
                    menuable.onMenuChange(index);
                }
            });
        }

        return view;
    }

    /** Met à jour l'icône d'un seul bouton selon l'index actif */
    private void updateButtonIcon(int index) {
        if (index == currentActivatedIndex) {
            buttons[index].setImageResource(iconsSelected[index]);
        } else {
            buttons[index].setImageResource(iconsNormal[index]);
        }
    }

    /** Rafraîchit toutes les icônes du menu */
    private void refreshAllIcons() {
        for (int i = 0; i < buttons.length; i++) {
            updateButtonIcon(i);
        }
    }

    /** Permet à ControlActivity de changer l'index actif depuis l'extérieur */
    public void setActiveIndex(int index) {
        currentActivatedIndex = index;
        if (buttons != null) {
            refreshAllIcons();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuable = null;
    }
}

