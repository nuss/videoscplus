package org.basislager.videocapture;

//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Map.Entry;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
//import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.ImageFormat;
//import android.hardware.Camera.Size;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.util.Log;

public class VideoCapture extends CordovaPlugin {
	private static final String TAG = "VideoCapture";
	protected CameraManager manager;
	// private CameraDevice mCameraDevice;
	// private CameraCaptureSession mCameraSession;
	// private String mCameraId;



	// constructor
//	public VideoCapture(Context context, CameraManager manager) {
	public VideoCapture() {
//		CameraContext context = new CameraContext(cordova.getActivity().getApplicationContext());
//		manager = context.getCameraManager();
//		Log.d(TAG, "manager: "+manager);
	}

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		Log.d(TAG, "execution invoked");
		try {
			if (action.equals("startCapture")) {
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

	private void startCapture(final int deviceID, final CallbackContext callbackContext) {
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					Context context = cordova.getActivity().getApplicationContext();
					CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
					System.out.println("manager: "+manager);
					// callback to OK
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
				} catch (Exception e) {
					callbackContext.error(e.getMessage());
				}
			}
		});
	}

	private void stopCapture(final int deviceID,
			final CallbackContext callbackContext) {
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					Log.d(TAG, "capturing stopped");
					// callback to OK
					callbackContext.sendPluginResult(new PluginResult(
							PluginResult.Status.OK));
				} catch (Exception e) {
					callbackContext.error(e.getMessage());
				}
			}
		});
	}

	private void setResolution(final int deviceID, int width, int height,
			final CallbackContext callbackContext) {
	}

}

class CameraContext {
	Context context;

	public CameraContext(Context context) {
		this.context = context;
	}

	public CameraManager getCameraManager() {
		return (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
	}
}