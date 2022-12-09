package fr.caensup.lsts.smb116.democyclevie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnExplicite;
    private Button btnImplicite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupère les boutons
        btnExplicite = findViewById(R.id.btnExplicite);
        btnImplicite = findViewById(R.id.btnImplicite);

        // On crée les écouteurs de clic
        btnExplicite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity2WithEplicitIntent();
            }
        });

        btnImplicite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity2WithImplicitIntent();
            }
        });

        Toast.makeText(this, "Fin onCreate", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Fin onStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Fin onResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Fin onPause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Fin onDestroy", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Fin onRestart", Toast.LENGTH_LONG).show();
    }

    private void launchMainActivity2WithEplicitIntent() {
        // Intention explicite : on donne le nom de la classe de l'activité que l'on veut démarrer
        Intent monIntention = new Intent(this, MainActivity2.class);
        startActivity(monIntention);
    }

    private void launchMainActivity2WithImplicitIntent() {
        // Intention implicite : on déclarer une action dans le manifeste et dire que l'activité 2
        // réagit à la demande de cette action. On pourra dire que cette action fait partie de la
        // catégorie "par défaut"
        Intent monIntent = new Intent();
        monIntent.setAction("bonjour.ACTION");
        startActivity(monIntent);
    }
}