package cn.bertsir.cameralibrary;

import android.app.Activity;
import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Bert on 2017/2/21.
 */
public class CameraUtils {

    public static CameraUtils instance = null;
    private SurfaceHolder mSurfaceholder;

    public static CameraUtils getInstance() {
        if(instance == null){
            instance = new CameraUtils();
        }
        return instance;
    }

    public void openCamera(final SurfaceView mSv, final Context mContext){
        mSurfaceholder = mSv.getHolder();
        mSurfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceholder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                CameraHelper.getInstance().operationCamera(true, (Activity) mContext,mSv,mSurfaceholder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }


    public void closeCamera(final SurfaceView mSv, final Context mContext){
        CameraHelper.getInstance().operationCamera(false, (Activity) mContext,mSv,mSurfaceholder);
    }

}
