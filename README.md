# FilterMenu

Android 列表筛选

## Screenshot

![](https://github.com/freeler/FilterMenu/blob/master/screenshot/screen001.png)
![](https://github.com/freeler/FilterMenu/blob/master/screenshot/screen002.png)
![](https://github.com/freeler/FilterMenu/blob/master/screenshot/screen003.png)
![](https://github.com/freeler/FilterMenu/blob/master/screenshot/screen004.png)


## 使用
- 方式 1

```java
暂时不提供仓库下载，功能持续添加中
```

- 方式 2. 拷贝Libs工程里面的lib_filterMenu到自己的工程里面，根据需求自行修改使用

## 注意点
dropDownMenu的父布局必须是RelativeLayout

## 范例

- 使用Builder方式创建

```java
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

private Filter getGenderFilter() {
    List<BaseBean<Integer, String>> options = new ArrayList<>();
    options.add(new BaseBean<>(1, "男"));
    options.add(new BaseBean<>(2, "女"));
    options.add(new BaseBean<>(0, "性别不明"));
    return new ListFilter<BaseBean<Integer, String>>(this)
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
    options.add(new BaseBean<>(1, "一居"));
    options.add(new BaseBean<>(2, "二居"));
    options.add(new BaseBean<>(3, "三居"));
    options.add(new BaseBean<>(4, "四居"));
    options.add(new BaseBean<>(5, "五居"));
    options.add(new BaseBean<>(99, "五居以上"));
    return new GridMultiFilter<BaseBean<Integer, String>>(this)
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
```

- 属性说明

| Attribute                  | 属性含义                                     | 默认值     |
|:---------------------------|:--------------------------------------------|:----------|
| menuDividerWidth | menu分割线宽度 | 0.5dp |
| menuDividerMarginTop | menu分割线上边距 | 0dp |
| menuDividerMarginBottom | menu分割线下边距 | 0dp |
| menuDividerColor | menu分割线颜色 | 0xffffffff |
| dividerLineHeight | divider高度 | 0.5dp |
| dividerMarginLeft | divider左边距 | 0dp |
| dividerMarginRight | divider右边距 | 0dp |
| dividerBackgroundColor | divider背景色 | 0xff777777 |
| menuTextSize | tab字体大小 | 14sp |
| menuHeight | 筛选menu高度 | 32dp      |
| menuMaxHeightPercent | 最大高度百分比 | 0.6f |
| menuBackgroundColor | 筛选menu背景色 | 0xffffffff |
| textSelectedColor | tab展开时对应标题文字颜色 |0xff000000 |
| textUnSelectedColor| tab关闭时对应标题文字颜色 | 0xff000000 |
| menuSelectedIcon | tab选中图标 | 无 |
| menuUnSelectedIcon | tab未选中图标 | 无 |
| menuIconPadding | tab标题文字和图标间距 | 4dp |
| maskColor | 遮罩颜色 | 0x99000000 |


- 方法说明

| Attribute                  | 方法含义                                     |
|:---------------------------|:--------------------------------------------|
| addFilterView           | 添加筛选项  |  
| addFilterViews          | 添加筛选项集合     |  
| withDefaultChanged | 是否需要自动触发默认筛选 |  
| addListener | 监听value的变化 |  