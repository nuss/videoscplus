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

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

//import org.apache.cordova.PluginResult;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.ImageFormat;
//import android.hardware.Camera.Size;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.params.StreamConfigurationMap;

public class VideoCapture extends CordovaPlugin {
	private static final String TAG = "VideoCapture";
	protected CameraManager manager;
	private CameraDevice mCameraDevice;
	private CameraCaptureSession mCameraSession;
	private static int vDeviceID;
	protected CameraCharacteristics cameraCharacteristics;
	private final ErrorDisplayer mErrorDisplayer;
	private final CameraReadyListener mReadyListener;
	private final Handler mReadyHandler;

	// constructor
	//	public VideoCapture(Context context, CameraManager manager) {
	public VideoCapture(ErrorDisplayer errorDisplayer, CameraReadyListener readyListener, Handler
						readyHandler) {
//		mCameraManager = cameraManager;
		mErrorDisplayer = errorDisplayer;
		mReadyListener = readyListener;
		mReadyHandler = readyHandler;
	}

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		Log.d(TAG, "execution invoked");
		try {
			if (action.equals("openCamera")) {
				openCamera(args.getInt(0), callbackContext);
			} else if (action.equals("startCapture")) {
				startCapture(callbackContext);
			} else if (action.equals("stopCapture")) {
				stopCapture(callbackContext);
			} else if (action.equals("setResolution")) {
				setResolution(args.getInt(0), args.getInt(1), callbackContext);
			} else {
				return false;
			}
		} catch (Exception e) {
			callbackContext.error(e.getMessage());
		}

		return true;
	}

	private void openCamera(final int deviceID, final CallbackContext callbackContext) {
		cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					String successMsg = "";
					Context context = cordova.getActivity().getApplicationContext();
					CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
//					System.out.println("manager: "+manager);
//					String[] deviceList = manager.getCameraIdList();
					for (String id : manager.getCameraIdList()) {
						CameraCharacteristics c = manager.getCameraCharacteristics(id);
						if(c.get(CameraCharacteristics.LENS_FACING) == deviceID) {
							vDeviceID = deviceID;
							if(vDeviceID == cameraCharacteristics.LENS_FACING_BACK) {
								successMsg = "Successfully opened backside camera";
							} else if (vDeviceID == cameraCharacteristics.LENS_FACING_FRONT) {
								successMsg = "Successfully opened frontside camera";
							} else {
								successMsg = "Successfully opened device "+vDeviceID;
							}
						};
						for (android.util.Size size : c.get(CameraCharacteristics
								.JPEG_AVAILABLE_THUMBNAIL_SIZES)) {
							System.out.println(id+": "+size);
						}
					}
					// callback to OK
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, successMsg));
				} catch (Exception e) {
					callbackContext.error(e.getMessage());
				}
			}
		});
	}

	private void startCapture(final CallbackContext callbackContext) {

	}

	private void stopCapture(final CallbackContext callbackContext) {
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

	private void setResolution(int width, int height, final CallbackContext callbackContext) {

	}

	/**
	 * Main listener for camera session events
	 * Invoked on mCameraThread
	 */
	private CameraCaptureSession.StateCallback mCameraSessionListener = new CameraCaptureSession
			.StateCallback() {

		@Override
		public void onConfigured(CameraCaptureSession session) {
			mCameraSession = session;
			mReadyHandler.post(new Runnable() {
				public void run() {
					// This can happen when the screen is turned off and turned back on.
					if (null == mCameraDevice) {
						return;
					}

					mReadyListener.onCameraReady();
				}
			});

		}

		@Override
		public void onConfigureFailed(CameraCaptureSession session) {
			mErrorDisplayer.showErrorDialog("Unable to configure the capture session");
			mCameraDevice.close();
			mCameraDevice = null;
		}
	};

	/**
	 * Simple listener for main code to know the camera is ready for requests, or failed to
	 * start.
	 */
	public interface CameraReadyListener {
		public void onCameraReady();
	}

	/**
	 * Simple listener for displaying error messages
	 */
	public interface ErrorDisplayer {
		public void showErrorDialog(String errorMessage);

		public String getErrorString(CameraAccessException e);
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