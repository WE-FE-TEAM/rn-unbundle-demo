package com.awesomeproject;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.cxxbridge.CatalystInstanceImpl;
import com.facebook.react.cxxbridge.JSBundleLoader;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

public class MainApplication extends Application implements ILemonReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        private HashMap<String, ReactInstanceManager> instanceMap = new HashMap<>();


        @Override
        public boolean getUseDeveloperSupport() {
            return false;
//      return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new DemoReactPackage()
            );
        }

        protected
        @Nullable
        String getBundleAssetName() {
            return "index.android.bundle";
        }

        protected ReactInstanceManager createReactInstanceManager() {
            ReactInstanceManagerBuilder builder = ReactInstanceManager.builder()
                    .setApplication(getApplication())
                    .setJSMainModuleName(getJSMainModuleName())
                    .setUseDeveloperSupport(getUseDeveloperSupport())
                    .setRedBoxHandler(getRedBoxHandler())
                    .setUIImplementationProvider(getUIImplementationProvider())
                    .setInitialLifecycleState(LifecycleState.BEFORE_CREATE);

            for (ReactPackage reactPackage : getPackages()) {
                builder.addPackage(reactPackage);
            }

            builder.setJSBundleLoader(new JSBundleLoader() {

                private static final String TAG = "MultiBundleLoader";

                @Override
                public String loadScript(CatalystInstanceImpl instance) {
                    //在加载bundle的时候，直接加载多个试试
                    String assetUrl = "assets://index.android.bundle";
//                    instance.loadScriptFromAssets(context.getAssets(), assetUrl);
                    String path2 = "assets://fund.android.bundle";

                    try{
                        Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromAssets", new Class[]{AssetManager.class, String.class});
                        method.setAccessible(true);

                        method.invoke(instance, new Object[]{ getApplication().getAssets(), assetUrl});
                    }catch(Exception e){
                        Log.e(TAG, e.getMessage(), e);
                    }

                    try{
                        Method method = CatalystInstanceImpl.class.getDeclaredMethod("jniLoadScriptFromAssets", new Class[]{AssetManager.class, String.class});
                        method.setAccessible(true);

                        method.invoke(instance, new Object[]{ getApplication().getAssets(), path2});
                    }catch(Exception e){
                        Log.e(TAG, e.getMessage(), e);
                    }
                    return assetUrl;
                }
            });
            return builder.build();
        }

        public ReactInstanceManager getReactInstanceManager(String businessModule) {
            ReactInstanceManager instance = null;
            if( instanceMap.containsKey(businessModule)){
                instance = instanceMap.get(businessModule);
            }

            if( instance == null ){
                instance =  createReactInstanceManager();
                instanceMap.put(businessModule, instance);
            }

            return instance;
        }
    };

    private final LemonReactNativeHost mLemonReactNativeHost = new LemonReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return false;
//      return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new DemoReactPackage()
            );
        }
    };
    
    public LemonReactNativeHost getLemonReactNativeHost(){
        return mLemonReactNativeHost;
    }

//    @Override
//    public ReactNativeHost getReactNativeHost() {
//        return mReactNativeHost;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);
    }
}
