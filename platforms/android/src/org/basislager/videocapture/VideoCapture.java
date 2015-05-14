package org.basislager.videocapture;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.util.Log;

public class VideoCapture extends CordovaPlugin {
	private static final String TAG = "VideoCapture";
	private CameraDevice mCameraDevice;
	private CameraCaptureSession mCameraSession;
	private static Context context;
	private CameraManager manager = (CameraManager) context
			.getSystemService(Context.CAMERA_SERVICE);

	/* constructor */
	public VideoCapture() {
		Log.v(TAG, "class VideoCapture initialized");
	}

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		Log.v(TAG, "execution invoked");
		try {
			if (action.equals("startCapture")) {
				Log.v(TAG, "start capturing");
				startCapture(args.getInt(0), callbackContext);
			} else if (action.equals("stopCapture")) {
				stopCapture(args.getInt(0), callbackContext);
			} else if (action.equals("setResolution")) {
				setResolution(args.getInt(0), args.getInt(1), args.getInt(2),
						callbackContext);
			} else {
				return false;
			}
		} catch (Exception e) {
			callbackContext.error(e.getMessage());
		}

		return true;
	}

	private void startCapture(final int deviceID,
			final CallbackContext callbackContext) {
		Log.v(TAG, "StartCapture called");
	}

	private void stopCapture(final int deviceID,
			final CallbackContext callbackContext) {
	}

	private void setResolution(final int deviceID, int width, int height,
			final CallbackContext callbackContext) {
	}
}