package com.freeler.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.freeler.flitermenu.FilterMenu;
import com.freeler.flitermenu.FilterView;
import com.freeler.flitermenu.helper.FilterViewHelper;
import com.freeler.flitermenu.listener.OnValueChangeListener;
import com.freeler.flitermenu.view.ListFilterView;
import com.freeler.flitermenu.listener.Convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FilterMenu filterMenu = findViewById(R.id.filterMenu);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        FilterView<Integer> filterView = new ListFilterView<Integer>(this)
                .setOptions(list)
                .setDisplayConvert(new Convert<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return integer == 1 ? "ok" : "no";
                    }
                })
                .setTitleName("我的小家")
                .setValue(1)
                .setValueToService(new Convert<Integer, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(final Integer integer) {
                        return new HashMap<String, Object>() {
                            {
                                put("key", integer + 10);
                            }
                        };
                    }
                });


        FilterViewHelper build = new FilterViewHelper.Builder(filterMenu)
                .withDefaultChanged(true)
                .addFilterView(filterView)
                .onValueChange(new OnValueChangeListener() {
                    @Override
                    public void changed(@Nullable FilterView filterView, @NonNull Map<String, Object> map) {
                        Log.e("filter", map.toString());
                    }
                }).build();

        FilterMenu filterMenu1 = new FilterMenu(this);
        filterMenu1.
    }
}
