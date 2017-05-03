package com.awesomeproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.test.ActivityTestCase;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.cxxbridge.CatalystInstanceImpl;
import java.lang.reflect.Method;

/**
 * Created by jess on 2017/3/9.
 */

public class LoadFileModule extends ReactContextBaseJavaModule {

    private static final String TAG = "LoadFileModule";

    public LoadFileModule(ReactApplicationContext reactContext){
        super(reactContext);
    }

    @Override
    public String getName() {
        return "LoadFileHelper";
    }

    @ReactMethod
    public void loadFile(String filePath){
        Log.d(TAG, "loadFile:" + filePath);

//        ReactApplication app = (ReactApplication) getCurrentActivity().getApplication();
//        ReactNativeHost host = app.getReactNativeHost();
//        CatalystInstanceImpl catalystInstance = (CatalystInstanceImpl)host.getReactInstanceManager().getCurrentReactContext().getCatalystInstance();
//
//        String path2 = "assets://fund.android.bundle";
//
//        try{
//            Method method = CatalystInstanceImpl.class.getDeclaredMethod("jniLoadScriptFromAssets", new Class[]{AssetManager.class, String.class});
//            method.setAccessible(true);
//            method.invoke(catalystInstance, new Object[]{ getCurrentActivity().getAssets(), path2});
//        }catch(Exception e){
//            Log.e(TAG, e.getMessage(), e);
//        }
    }

    @ReactMethod
    public void openFundPage(){
        final Activity activity = getCurrentActivity();

        final Intent intent = new Intent(activity, FundActivity2.class);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(intent);
            }
        });
    }

    @ReactMethod
    public void reloadReactInstanceManager(){
        final Activity activity = getCurrentActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LemonActivity ac = (LemonActivity) getCurrentActivity();

                ac.getReactInstanceManager().recreateReactContextInBackground();
            }
        });
    }
}
