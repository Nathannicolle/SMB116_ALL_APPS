package fr.caensup.lsts.smb116.hellowebview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Récupération de l'objet depuis le layout
        webview = findViewById(R.id.wvNavigateur);
        webview.getSettings().setJavascriptEnabled("true");
        webview.loadUrl("https://www.cnam.fr/");
    }
}