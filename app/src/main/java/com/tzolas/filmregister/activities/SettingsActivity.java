package com.tzolas.filmregister.activities;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import com.tzolas.filmregister.R;
import android.content.DialogInterface;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.btnChangeLanguage).setOnClickListener(v -> showLanguageDialog());
    }

    private void showLanguageDialog() {
        final String[] languages = {"Español", "English", "Euskera"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_language));
        builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String lang = "es";  // Por defecto español
                if (which == 1) {
                    lang = "en";
                } else if (which == 2) {
                    lang = "eu";
                }
                setLocale(lang);  // Método heredado de BaseActivity
                dialog.dismiss();
                recreate(); // Recrea la actividad para aplicar el cambio
            }
        });
        builder.create().show();
    }
}
