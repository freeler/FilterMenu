# FilterMenu

Android 列表筛选

## Screenshot

![]()


## 使用
- 方式 1

```java
```

- 方式 2. 拷贝Libs工程里面的lib_filterMenu到自己的工程里面

## 注意点
dropDownMenu的父布局必须是RelativeLayout

## 范例

- 使用Builder方式创建

```java
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