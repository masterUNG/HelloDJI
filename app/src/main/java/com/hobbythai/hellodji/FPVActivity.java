package com.hobbythai.hellodji;

import android.os.Bundle;
import android.util.Log;

import dji.sdk.api.DJIDrone;
import dji.sdk.api.DJIDroneTypeDef;
import dji.sdk.api.DJIError;
import dji.sdk.interfaces.DJIGerneralListener;
import dji.sdk.interfaces.DJIReceivedVideoDataCallBack;
import dji.sdk.widget.DjiGLSurfaceView;

public class FPVActivity extends DemoBaseActivity {

    private static String TAG = "MyFPV";
    private DJIReceivedVideoDataCallBack mReceivedVideoDataCallBack = null;
    private DjiGLSurfaceView mDjiGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpv);

        new Thread() {
            public void run() {
                try {

                    DJIDrone.checkPermission(getApplicationContext(), new DJIGerneralListener() {
                        @Override
                        public void onGetPermissionResult(int result) {
                            if (result == 0) {
                                //show success
                                Log.e(TAG, "Permission = " + result);
                                Log.e(TAG, "Permission = " + DJIError.getCheckPermissionErrorDescription(result));
                            } else {
                                //show errors
                                Log.e(TAG, "Permission = " + result);
                                Log.e(TAG, "Permission = " + DJIError.getCheckPermissionErrorDescription(result));
                            }//if

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }//catch

            }//run
        }.start();

        DJIDrone.initWithType(this.getApplicationContext(), DJIDroneTypeDef.DJIDroneType.DJIDrone_Phantom3_Advanced);

        DJIDrone.connectToDrone(); // Connect to the drone

        mDjiGLSurfaceView = (DjiGLSurfaceView) findViewById(R.id.DjiSurfaceView_02);
        mDjiGLSurfaceView.start();

        mReceivedVideoDataCallBack = new DJIReceivedVideoDataCallBack() {
            @Override
            public void onResult(byte[] videoBuffer, int size) {
                mDjiGLSurfaceView.setDataToDecoder(videoBuffer, size);
            }
        };

        DJIDrone.getDjiCamera().setReceivedVideoDataCallBack(mReceivedVideoDataCallBack);

    }//on create

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DJIDrone.getDjiCamera() != null) {
            DJIDrone.getDjiCamera().setReceivedVideoDataCallBack(null);
        }
        mDjiGLSurfaceView.destroy();
    }//on destroy

}//main
