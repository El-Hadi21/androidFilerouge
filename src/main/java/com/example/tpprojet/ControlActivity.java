package com.example.tpprojet;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class ControlActivity extends AppCompatActivity implements Menuable, Notifiable {

    private static final String TAG = "ControlActivity";

    // Tableau des 7 fragments disponibles
    private final Fragment[] tabFragments = {
            new Screen1Fragment(),
            new Screen2Fragment(),
            new Screen3Fragment(),
            new Screen4Fragment(),
            new Screen5Fragment(),
            new Screen6Fragment(),
            new Screen7Fragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        // Récupère le numéro de menu passé par MainActivity (défaut = 0)
        int menuIndex = getIntent().getIntExtra("menuIndex", 0);

        // Charge MenuFragment en lui indiquant quel menu est actif
        MenuFragment menuFragment = MenuFragment.newInstance(menuIndex);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentMenu, menuFragment)
                .commit();

        // Charge le fragment principal correspondant
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentScreen, tabFragments[menuIndex])
                .commit();
    }

    // ─── Menuable ───────────────────────────────────────────────────────────────
    // Appelé par MenuFragment quand l'utilisateur change de menu
    @Override
    public void onMenuChange(int index) {
        Log.d(TAG, "onMenuChange → fragment #" + index);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentScreen, tabFragments[index])
                .commit();
    }

    // ─── Notifiable ─────────────────────────────────────────────────────────────
    // Appelé par Screen1Fragment lors du clic sur le bouton GO
    @Override
    public void onClick(int numFragment) {
        Log.d(TAG, "onClick reçu depuis fragment #" + numFragment);
    }

    @Override
    public void onDataChange(int numFragment, Object object) {
        Log.d(TAG, "onDataChange depuis fragment #" + numFragment + " | data=" + object);
    }
}

