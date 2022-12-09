package fr.caensup.lsts.smb116.capteurs;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvListeCapteurs;
    private TextView tvMesure;
    private SensorManager sensorManager;
    private Sensor sensorGravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvListeCapteurs = findViewById(R.id.tvListCapteurs);
        tvListeCapteurs.setText(getListeCapteurs());
        tvMesure = findViewById(R.id.tvMesure);
        sensorManager = (SensorManager) (this.getSystemService(SENSOR_SERVICE));
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private String getListeCapteurs() {
        SensorManager sensorManager= (SensorManager) (this.getSystemService(SENSOR_SERVICE));
        List<Sensor> listeCapteurs = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder capteurs = new StringBuilder();
        // capteurs.append("Nombre de capteurs");
        for(Sensor s : listeCapteurs) {
            capteurs.append(
                    s.getName()
                            + " " + s.getVendor()
                            + " " + s.getPower() + "mA"
                            + " " + s.getStringType()
                            + " " + s.getMinDelay() + " micro-s \n");
        }

        return capteurs.toString();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float gx = sensorEvent.values[0];
        float gy = sensorEvent.values[1];
        float gz = sensorEvent.values[2];
        String texte = "Gx : " + new DecimalFormat("0.00");
        tvMesure.setText((CharSequence) sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}