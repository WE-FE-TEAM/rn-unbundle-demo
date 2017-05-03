package com.awesomeproject;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;

public class MainActivity extends LemonActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "AwesomeProject";
    }

    protected LemonActivityDelegate createReactActivityDelegate() {
        LemonActivityDelegate delegate = new LemonActivityDelegate(this, getMainComponentName(), "index");
        return delegate;
    }
}
