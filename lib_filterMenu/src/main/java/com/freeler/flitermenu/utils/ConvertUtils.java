package com.freeler.flitermenu.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author: xuzeyang
 * @Date: 2020/3/12
 */
public class ConvertUtils {

    public static int dp2Px(float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics()) + 0.5);
    }

}
