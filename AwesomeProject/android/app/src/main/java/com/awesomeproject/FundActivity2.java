package com.awesomeproject;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.cxxbridge.CatalystInstanceImpl;

import java.lang.reflect.Method;


public class FundActivity2 extends ReactActivity {

    private static final String TAG = "FundActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReactApplication app = (ReactApplication) getApplication();
        ReactNativeHost host = app.getReactNativeHost();
        CatalystInstanceImpl catalystInstance = (CatalystInstanceImpl)host.getReactInstanceManager().getCurrentReactContext().getCatalystInstance();

        String path2 = "assets://fund.android.bundle";
        Log.e(TAG, "加载fund.android.bundle之前");
        try{
            Method method = CatalystInstanceImpl.class.getDeclaredMethod("jniLoadScriptFromAssets", new Class[]{AssetManager.class, String.class});
            method.setAccessible(true);
            method.invoke(catalystInstance, new Object[]{ getAssets(), path2});
        }catch(Exception e){
            Log.e(TAG, e.getMessage(), e);
        }
        Log.e(TAG, "加载fund.android.bundle之后");
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "fund";
    }

    @Override
    public void onBackPressed() {
        super.invokeDefaultOnBackPressed();
    }
}
