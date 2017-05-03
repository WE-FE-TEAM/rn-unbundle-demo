package com.awesomeproject;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactPackage;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.cxxbridge.CatalystInstanceImpl;
import com.facebook.react.cxxbridge.JSBundleLoader;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.UIImplementationProvider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by jess on 2017/5/2.
 * 内部根据业务，维护ReactInstanceManager 的池子，便于复用以及按业务来增量load JS bundle
 */

public abstract class LemonReactNativeHost {

    private HashMap<String, ReactInstanceManager> instanceMap = new HashMap<>();

    private final Application mApplication;

    public LemonReactNativeHost(Application application) {
        mApplication = application;
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

    /**
     * Get the {@link RedBoxHandler} to send RedBox-related callbacks to.
     */
    protected @Nullable
    RedBoxHandler getRedBoxHandler() {
        return null;
    }

    protected final Application getApplication() {
        return mApplication;
    }

    /**
     * Get the {@link UIImplementationProvider} to use. Override this method if you want to use a
     * custom UI implementation.
     *
     * Note: this is very advanced functionality, in 99% of cases you don't need to override this.
     */
    protected UIImplementationProvider getUIImplementationProvider() {
        return new UIImplementationProvider();
    }

    /**
     * Returns the name of the main module. Determines the URL used to fetch the JS bundle
     * from the packager server. It is only used when dev support is enabled.
     * This is the first file to be executed once the {@link ReactInstanceManager} is created.
     * e.g. "index.android"
     */
    protected String getJSMainModuleName() {
        return "index.android";
    }

    /**
     * Returns a custom path of the bundle file. This is used in cases the bundle should be loaded
     * from a custom path. By default it is loaded from Android assets, from a path specified
     * by {@link getBundleAssetName}.
     * e.g. "file://sdcard/myapp_cache/index.android.bundle"
     */
    protected @Nullable String getJSBundleFile() {
        return null;
    }

    /**
     * Returns the name of the bundle in assets. If this is null, and no file path is specified for
     * the bundle, the app will only work with {@code getUseDeveloperSupport} enabled and will
     * always try to load the JS bundle from the packager server.
     * e.g. "index.android.bundle"
     */
    protected @Nullable String getBundleAssetName() {
        return "index.android.bundle";
    }

    /**
     * Returns whether dev mode should be enabled. This enables e.g. the dev menu.
     */
    public abstract boolean getUseDeveloperSupport();

    /**
     * Returns a list of {@link ReactPackage} used by the app.
     * You'll most likely want to return at least the {@code MainReactPackage}.
     * If your app uses additional views or modules besides the default ones,
     * you'll want to include more packages here.
     */
    protected abstract List<ReactPackage> getPackages();
}
