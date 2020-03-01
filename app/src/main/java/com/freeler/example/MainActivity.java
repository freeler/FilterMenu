package com.freeler.example;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freeler.flitermenu.DropDownMenu;
import com.freeler.flitermenu.Filter;
import com.freeler.flitermenu.helper.FilterViewHelper;
import com.freeler.flitermenu.listener.Convert;
import com.freeler.flitermenu.listener.OnValueChangeListener;
import com.freeler.flitermenu.view.ListFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DropDownMenu dropDownMenu = findViewById(R.id.dropDownMenu);
        TextView tvContent = findViewById(R.id.tvContent);
        init(dropDownMenu, tvContent);
    }

    private void init(DropDownMenu dropDownMenu, final TextView tvContent) {
        new FilterViewHelper.Builder(dropDownMenu)
                .withDefaultChanged(true)
                .addFilterView(getGenderFilter())
                .addFilterView(getAgeFilter())
                .addListener(new OnValueChangeListener() {
                    @Override
                    public void changed(@Nullable Filter filterView, @NonNull Map<String, Object> map) {
                        tvContent.setText(map.toString());
                    }
                }).build();
    }


    private Filter getGenderFilter() {
        List<Integer> options = new ArrayList<>();
        options.add(1);
        options.add(2);
        return new ListFilter<Integer>(this)
                .setNeedAll(true)
                .setOptions(options)
                .setDisplayConvert(new Convert<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return integer == 1 ? "男" : integer == 2 ? "女" : "性别不明";
                    }
                })
                .setTitleName("性别")
                .setValue(1)
                .setValueConvert(new Convert<Integer, Map<String, Integer>>() {
                    @Override
                    public Map<String, Integer> apply(final Integer integer) {
                        return new HashMap<String, Integer>() {{
                            put("gender", integer);
                        }};
                    }
                });
    }

    private Filter getAgeFilter() {
        List<Integer> options = new ArrayList<>();
        options.add(10);
        options.add(20);
        options.add(30);
        return new ListFilter<Integer>(this)
                .setNeedAll(true)
                .setOptions(options)
                .setDisplayConvert(new Convert<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return integer + "岁";
                    }
                })
                .setTitleName("年龄")
                .setValue(10)
                .setValueConvert(new Convert<Integer, Map<String, Integer>>() {
                    @Override
                    public Map<String, Integer> apply(final Integer integer) {
                        return new HashMap<String, Integer>() {{
                            put("age", integer);
                        }};
                    }
                });
    }

}
