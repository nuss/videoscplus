package org.basislager.videocapture;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by stefan on 19.05.15.
 */
public class VideoCaptureActivity extends Activity {
    private static final String TAG = "VideoCaptureActivity";
//    private static Context context;
//    private final CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        System.out.println(manager);
        if (null == savedInstanceState) {
            Log.i(TAG, "savedInstanceState");
        }
    }
}
