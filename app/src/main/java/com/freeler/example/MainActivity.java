package com.freeler.example;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freeler.flitermenu.bean.BaseBean;
import com.freeler.flitermenu.helper.Filter;
import com.freeler.flitermenu.helper.FilterViewHelper;
import com.freeler.flitermenu.helper.grid.GridListFilter;
import com.freeler.flitermenu.helper.list.ListFilter;
import com.freeler.flitermenu.listener.Convert;
import com.freeler.flitermenu.listener.OnValueChangeListener;
import com.freeler.flitermenu.view.DropDownMenu;

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
                .addFilterView(getRoomFilter())
                .addListener(new OnValueChangeListener() {
                    @Override
                    public void changed(@Nullable Filter filterView, @NonNull Map<String, Object> map) {
                        tvContent.setText(map.toString());
                    }
                }).build();
    }


    private Filter getGenderFilter() {
        List<BaseBean<Integer, String>> options = new ArrayList<>();
        options.add(new BaseBean<>(1, "男"));
        options.add(new BaseBean<>(2, "女"));
        options.add(new BaseBean<>(0, "性别不明"));
        return new ListFilter<BaseBean<Integer, String>>(this)
                .setNeedAll(true)
                .setOptions(options)
                .setDisplayConvert(new Convert<BaseBean<Integer, String>, String>() {
                    @Override
                    public String apply(BaseBean<Integer, String> baseBean) {
                        return baseBean.getT2();
                    }
                })
                .setTitleName("性别")
                .setValue(options.get(0))
                .setValueConvert(new Convert<BaseBean<Integer, String>, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(final BaseBean<Integer, String> baseBean) {
                        return new HashMap<String, Object>() {{
                            put("gender", baseBean == null ? null : baseBean.getT1());
                        }};
                    }
                });
    }

    private Filter getAgeFilter() {
        List<BaseBean<Integer, String>> options = new ArrayList<>();
        options.add(new BaseBean<>(10, "十岁"));
        options.add(new BaseBean<>(20, "二十岁"));
        options.add(new BaseBean<>(30, "三十岁"));
        return new ListFilter<BaseBean<Integer, String>>(this)
                .setNeedAll(true)
                .setOptions(options)
                .setDisplayConvert(new Convert<BaseBean<Integer, String>, String>() {
                    @Override
                    public String apply(BaseBean<Integer, String> baseBean) {
                        return baseBean.getT2();
                    }
                })
                .setTitleName("年龄")
                .setValue(options.get(0))
                .setValueConvert(new Convert<BaseBean<Integer, String>, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(final BaseBean<Integer, String> baseBean) {
                        return new HashMap<String, Object>() {{
                            put("age", baseBean == null ? null : baseBean.getT1());
                        }};
                    }
                });
    }

    private Filter getRoomFilter() {
        List<BaseBean<Integer, String>> options = new ArrayList<>();
        options.add(new BaseBean<>(1, "一室"));
        options.add(new BaseBean<>(2, "二室"));
        options.add(new BaseBean<>(3, "三室"));
        options.add(new BaseBean<>(4, "四室"));
        options.add(new BaseBean<>(5, "五室"));
        options.add(new BaseBean<>(99, "五室以上"));
        return new GridListFilter<BaseBean<Integer, String>>(this)
                .setHeadName("房型选择")
                .setOptions(options)
                .setMultiDisplayConvert(new Convert<BaseBean<Integer, String>, String>() {
                    @Override
                    public String apply(BaseBean<Integer, String> baseBean) {
                        return baseBean.getT2();
                    }
                })
                .setTitleName("房型选择")
                .setValue(null)
                .setValueConvert(new Convert<List<BaseBean<Integer, String>>, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(final List<BaseBean<Integer, String>> baseBeans) {
                        return new HashMap<String, Object>() {{
                            if (baseBeans != null) {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < baseBeans.size(); i++) {
                                    stringBuilder.append(baseBeans.get(i).getT1());
                                    if (i < baseBeans.size() - 1) {
                                        stringBuilder.append(",");
                                    }
                                }
                                put("room", stringBuilder.length() == 0 ? null : stringBuilder.toString());
                            } else {
                                put("room", null);
                            }
                        }};
                    }
                });
    }

}
