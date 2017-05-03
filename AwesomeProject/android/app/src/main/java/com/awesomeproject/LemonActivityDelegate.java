package com.awesomeproject;

/**
 * Created by jess on 2017/5/2.
 */


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Callback;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionListener;

import javax.annotation.Nullable;

public class LemonActivityDelegate {


    private final int REQUEST_OVERLAY_PERMISSION_CODE = 1111;
    private static final String REDBOX_PERMISSION_GRANTED_MESSAGE =
            "Overlay permissions have been granted.";
    private static final String REDBOX_PERMISSION_MESSAGE =
            "Overlay permissions needs to be granted in order for react native apps to run in dev mode";

    private final @Nullable Activity mActivity;
    private final @Nullable FragmentActivity mFragmentActivity;
    private final @Nullable String mMainComponentName;

    private @Nullable
    ReactRootView mReactRootView;
    private @Nullable DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
    private @Nullable PermissionListener mPermissionListener;
    private @Nullable Callback mPermissionsCallback;

    private ReactInstanceManager mReactInstanceManager;

    private String mBusinessModule = "common";

    public LemonActivityDelegate(Activity activity, @Nullable String mainComponentName, String businessModule) {
        mActivity = activity;
        mMainComponentName = mainComponentName;
        mFragmentActivity = null;
        mBusinessModule = businessModule;
    }

    public LemonActivityDelegate(
            FragmentActivity fragmentActivity,
            @Nullable String mainComponentName,
            String businessModule) {
        mFragmentActivity = fragmentActivity;
        mMainComponentName = mainComponentName;
        mActivity = null;
        mBusinessModule = businessModule;
    }

    protected @Nullable Bundle getLaunchOptions() {
        return null;
    }

    protected ReactRootView createRootView() {
        return new ReactRootView(getContext());
    }

    /**
     * Get the {@link LemonReactNativeHost} used by this app. By default, assumes
     * {@link Activity#getApplication()} is an instance of {@link ReactApplication} and calls
     * {@link ReactApplication#getReactNativeHost()}. Override this method if your application class
     * does not implement {@code ReactApplication} or you simply have a different mechanism for
     * storing a {@code LemonReactNativeHost}, e.g. as a static field somewhere.
     */
    protected LemonReactNativeHost getReactNativeHost() {
        ILemonReactApplication app = (ILemonReactApplication) getPlainActivity().getApplication();
        return ((ILemonReactApplication) getPlainActivity().getApplication()).getLemonReactNativeHost();
    }

    public ReactInstanceManager getReactInstanceManager() {
        return mReactInstanceManager;
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean needsOverlayPermission = false;
        if (getReactNativeHost().getUseDeveloperSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Get permission to show redbox in dev builds.
            if (!Settings.canDrawOverlays(getContext())) {
                needsOverlayPermission = true;
                Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getContext().getPackageName()));
                FLog.w(ReactConstants.TAG, REDBOX_PERMISSION_MESSAGE);
                Toast.makeText(getContext(), REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show();
                ((Activity) getContext()).startActivityForResult(serviceIntent, REQUEST_OVERLAY_PERMISSION_CODE);
            }
        }

        mReactInstanceManager = getReactNativeHost().getReactInstanceManager(mBusinessModule);

        if (mMainComponentName != null && !needsOverlayPermission) {
            loadApp(mMainComponentName);
        }
        mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
    }

    protected void loadApp(String appKey) {
        if (mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        }
        mReactRootView = createRootView();
        mReactRootView.startReactApplication(
                getReactInstanceManager(),
                appKey,
                getLaunchOptions());
        getPlainActivity().setContentView(mReactRootView);
    }

    protected void onPause() {
        getReactInstanceManager().onHostPause(getPlainActivity());
    }

    protected void onResume() {
        getReactInstanceManager().onHostResume(
                getPlainActivity(),
                (DefaultHardwareBackBtnHandler) getPlainActivity());

        if (mPermissionsCallback != null) {
            mPermissionsCallback.invoke();
            mPermissionsCallback = null;
        }
    }

    protected void onDestroy() {
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
        getReactInstanceManager().onHostDestroy(getPlainActivity());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getReactInstanceManager()
                .onActivityResult(getPlainActivity(), requestCode, resultCode, data);
        if ( true ) {

        } else {
            // Did we request overlay permissions?
            if (requestCode == REQUEST_OVERLAY_PERMISSION_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(getContext())) {
                    if (mMainComponentName != null) {
                        loadApp(mMainComponentName);
                    }
                    Toast.makeText(getContext(), REDBOX_PERMISSION_GRANTED_MESSAGE, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (getReactNativeHost().getUseDeveloperSupport()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                getReactInstanceManager().showDevOptionsDialog();
                return true;
            }
            boolean didDoubleTapR = Assertions.assertNotNull(mDoubleTapReloadRecognizer)
                    .didDoubleTapR(keyCode, getPlainActivity().getCurrentFocus());
            if (didDoubleTapR) {
                getReactInstanceManager().getDevSupportManager().handleReloadJS();
                return true;
            }
        }
        return false;
    }

    public boolean onBackPressed() {
        getReactInstanceManager().onBackPressed();
        return true;
    }

    public boolean onNewIntent(Intent intent) {
        getReactInstanceManager().onNewIntent(intent);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(
            String[] permissions,
            int requestCode,
            PermissionListener listener) {
        mPermissionListener = listener;
        getPlainActivity().requestPermissions(permissions, requestCode);
    }

    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        mPermissionsCallback = new Callback() {
            @Override
            public void invoke(Object... args) {
                if (mPermissionListener != null && mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                    mPermissionListener = null;
                }
            }
        };
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        return Assertions.assertNotNull(mFragmentActivity);
    }

    private Activity getPlainActivity() {
        return ((Activity) getContext());
    }
}
