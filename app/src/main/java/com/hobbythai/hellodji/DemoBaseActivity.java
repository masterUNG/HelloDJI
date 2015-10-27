package com.hobbythai.hellodji;

import android.app.Activity;

import dji.midware.data.manager.P3.ServiceManager;

public class DemoBaseActivity extends Activity{

    @Override
    protected void onResume() {
        super.onResume();
        ServiceManager.getInstance().pauseService(false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        ServiceManager.getInstance().pauseService(true);
    }

}
