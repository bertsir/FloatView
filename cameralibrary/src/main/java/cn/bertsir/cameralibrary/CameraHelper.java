package cn.bertsir.cameralibrary;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Bert on 2017/2/21.
 */
public class CameraHelper {
    private static CameraHelper instance = null;
    private Camera mCamera;


    public static CameraHelper getInstance() {
        if(instance == null){
            instance = new CameraHelper();
        }
        return instance;
    }


    /**
     * 打开或者关闭摄像头
     * @param open
     * @param activity
     * @param sv
     * @param mSurfaceholder
     */
    public void operationCamera(boolean open, Activity activity, SurfaceView sv, SurfaceHolder mSurfaceholder){
        if(open){
            try {
                mCamera = Camera.open();
                mCamera.setAutoFocusMoveCallback(new Camera.AutoFocusMoveCallback() {
                    @Override
                    public void onAutoFocusMoving(boolean start, Camera camera) {
                        // Log.e(TAG, "onAutoFocusMoving: "+start );
                    }
                });

                mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                    @Override
                    public void onPreviewFrame(byte[] data, Camera camera) {
                    }
                });
                setCameraDisplayOrientation(activity,Camera.CameraInfo.CAMERA_FACING_BACK,
                        mCamera);
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFocusMode("continuous-video");
                // parameters.setZoom(10);
                //parameters.setPreviewSize(320, 240);
                // parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                mCamera.setParameters(parameters);
                mCamera.setPreviewDisplay(mSurfaceholder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
                sv.setBackgroundColor(Color.BLACK);
            }
        }else{
            mCamera.release();
            mCamera = null;
        }

    }


    /**
     * 旋转摄像头方向
     * @param activity
     * @param cameraId
     * @param camera
     */
    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int degrees = getDisplayRotation(activity);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    /**
     * 获得摄像头方向
     * @param activity
     * @return
     */
    private int getDisplayRotation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }
}
