package com.github.olegschwann.spritzreader.spritz_reader;

import android.app.Activity;
import android.support.wear.widget.SwipeDismissFrameLayout;
import android.util.Log;

public class DismissCallback extends SwipeDismissFrameLayout.Callback {
    @Override
    public void onDismissed(SwipeDismissFrameLayout layout) {
        Activity host = (Activity)layout.getContext();

    }
}

/* The only documentation:
https://developer.android.com/reference/android/support/wear/widget/SwipeDismissFrameLayout.html#addCallback(android.support.wear.widget.SwipeDismissFrameLayout.Callback)
https://github.com/Rayduh/support/blob/master/wear/src/android/support/wear/widget/SwipeDismissFrameLayout.java
https://github.com/Rayduh/support/blob/master/wear/tests/src/android/support/wear/widget/SwipeDismissPreferenceFragment.java
https://developer.android.google.cn/reference/kotlin/androidx/wear/widget/SwipeDismissFrameLayout.Callback.html
 */