/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lightson;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * My first Android app to go to the Play Store.  This app was
 * derived from http://www.mkyong.com/android/how-to-turn-onoff-camera-ledflashlight-in-android/
 */
public class LightsOnActivity extends Activity {
    //flag to detect flash is on or off
    private boolean isLighOn = false;

    private Camera camera;

    private Button button;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.buttonFlashlight);

        Context context = this;
        PackageManager pm = context.getPackageManager();

        // if device support camera?
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e("err", "Device has no camera!");
            return;
        }

        camera = Camera.open();
        final Camera.Parameters p = camera.getParameters();

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                if (isLighOn) {

                    Log.i("info", "torch is turn off!");

                    p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    button.setText(R.string.lightsOn_txt);
                    isLighOn = false;

                } else {

                    Log.i("info", "torch is turn on!");

                    p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(p);
                    camera.startPreview();
                    button.setText(R.string.lightsOff_txt);
                    isLighOn = true;

                }

            }
        });

    }
}
