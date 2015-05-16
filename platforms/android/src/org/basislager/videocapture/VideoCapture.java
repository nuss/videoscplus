package org.basislager.videocapture;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera.Size;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;

public class VideoCapture extends CordovaPlugin {
	private static final String TAG = "VideoCapture";
	private CameraDevice mCameraDevice;
	private CameraCaptureSession mCameraSession;
	private static Context context;
	private String mCameraId;
	private CameraManager manager = (CameraManager) context
			.getSystemService(Context.CAMERA_SERVICE);

	/* constructor */
	public VideoCapture() {
		Log.d(TAG, "class VideoCapture initialized");
	}

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		Log.d(TAG, "execution invoked");
		try {
			if (action.equals("startCapture")) {
				Log.d(TAG, "start capturing");
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
	}

	private void stopCapture(final int deviceID,
			final CallbackContext callbackContext) {
	}

	private void setResolution(final int deviceID, int width, int height,
			final CallbackContext callbackContext) {
	}

	private void setUpCameraOutputs(int width, int height) {
		CameraManager manager = (CameraManager) context
				.getSystemService(Context.CAMERA_SERVICE);
		try {
			for (String cameraId : manager.getCameraIdList()) {
				CameraCharacteristics characteristics = manager
						.getCameraCharacteristics(cameraId);

				// We don't use a front facing camera in this sample.
				if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
					continue;
				}

				StreamConfigurationMap map = characteristics
						.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

				// For still image captures, we use the largest available size.
				Size largest = Collections.max(
						Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
						new CompareSizesByArea());
				mImageReader = ImageReader.newInstance(largest.getWidth(),
						largest.getHeight(), ImageFormat.JPEG, /* maxImages */
						2);
				mImageReader.setOnImageAvailableListener(
						mOnImageAvailableListener, mBackgroundHandler);

				// Danger, W.R.! Attempting to use too large a preview size
				// could exceed the camera
				// bus' bandwidth limitation, resulting in gorgeous previews but
				// the storage of
				// garbage capture data.
				mPreviewSize = chooseOptimalSize(
						map.getOutputSizes(SurfaceTexture.class), width,
						height, largest);

				// We fit the aspect ratio of TextureView to the size of preview
				// we picked.
				int orientation = getResources().getConfiguration().orientation;
				if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
					mTextureView.setAspectRatio(mPreviewSize.getWidth(),
							mPreviewSize.getHeight());
				} else {
					mTextureView.setAspectRatio(mPreviewSize.getHeight(),
							mPreviewSize.getWidth());
				}

				mCameraId = cameraId;
				return;
			}
		} catch (CameraAccessException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			// Currently an NPE is thrown when the Camera2API is used but not
			// supported on the
			// device this code runs.
			// new ErrorDialog().show(getFragmentManager(), "dialog");
		}
	}
}