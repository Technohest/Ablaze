package com.example.boking.ablaze;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton flashButton;
    private ImageButton discoButton;
    private Camera camera;
    private boolean isFlashOn;
    private Camera.Parameters p;
    private Thread t;
    private boolean disco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isFlashOn = false;
        disco = false;
        flashButton = (ImageButton) findViewById(R.id.lightButton);
        discoButton = (ImageButton) findViewById(R.id.discoButton);

        camera = Camera.open();
        p = camera.getParameters();



        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLight();
            }
        });

        discoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(disco){
                    disco = false;
                }else{
                    t = new Thread(new Runnable() {
                        public void run() {
                            try {
                                discoLight();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                }
            }
        });
    }

    private void switchLight(){
        if(!isFlashOn) {
            isFlashOn = true;
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
        }else{
            isFlashOn = false;
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
        }
    }

    private void discoLight() throws InterruptedException {
        disco = true;
        while(disco) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            Thread.sleep(10);
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(p);
            camera.stopPreview();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
