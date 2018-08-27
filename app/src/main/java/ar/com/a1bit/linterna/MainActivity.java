package ar.com.a1bit.linterna;

import android.hardware.Camera;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Parameter;
import java.security.Policy;

public class MainActivity extends AppCompatActivity {

    private static final String WAKE_LOG_TAG = "flashLight";
    private Camera camera;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //turn on flash
        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();

        //Wake lock
        PowerManager powMan = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powMan.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"flashLight");
        wakeLock.setReferenceCounted(false);
        if(wakeLock.isHeld()){
            wakeLock.acquire();
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        //turn off
        camera.startPreview();
        camera.release();
        wakeLock.release();

    }
}
