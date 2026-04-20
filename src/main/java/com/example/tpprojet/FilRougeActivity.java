package com.example.tpprojet;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FilRougeActivity extends AppCompatActivity {

    private AnimationDrawable incidentAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fil_rouge);

        ImageView incidentImage = findViewById(R.id.incident_image);

        incidentAnimation = (AnimationDrawable) incidentImage.getBackground();

        incidentAnimation.start();

        // 1. On relie nos variables Java aux boutons du XML
        Button btnDefault = findViewById(R.id.btn_default);
        Button btnOption1 = findViewById(R.id.btn_option1);

        // 2. Gestion du clic sur "DEFAULT"
        btnDefault.setOnClickListener(v -> {
            // Un Intent simple pour changer d'écran
            Intent intent = new Intent(this, ControlActivity.class);
            startActivity(intent);
        });

        // 3. Gestion du clic sur "OPTION1"
        btnOption1.setOnClickListener(v -> {
            // Un Intent qui transporte une information
            Intent intent = new Intent(this, ControlActivity.class);
            // On glisse le paramètre "2" avec une clé pour le retrouver plus tard
            intent.putExtra("MENU_INDEX", 2);
            startActivity(intent);
        });
    }
}