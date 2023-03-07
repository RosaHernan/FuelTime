package sv.edu.utec.fueltimeapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Inicio.class);
                startActivity(i);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea,3000);

    }
}