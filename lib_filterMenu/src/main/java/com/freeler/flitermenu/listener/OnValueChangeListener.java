package com.freeler.flitermenu.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.freeler.flitermenu.helper.Filter;

import java.util.Map;

/**
 * helper变化监听
 *
 * @author: freeler
 * @Date: 2020/1/13
 */
public interface OnValueChangeListener {
    void changed(@Nullable Filter filterView, @NonNull Map<String, Object> map);
}
