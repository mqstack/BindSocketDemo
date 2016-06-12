package org.mqstack.bindsocket.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mq on 16/5/30.
 */

public class ToastUtil {

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }
}
