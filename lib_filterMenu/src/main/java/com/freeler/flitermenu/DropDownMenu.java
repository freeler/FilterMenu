package com.freeler.flitermenu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 筛选项
 *
 * @author: freeler
 * @Date: 2020/1/9
 */
public class DropDownMenu extends LinearLayout {

    private static final int DEFAULT_ICON_PADDING = dp2Px(4f);

    private static final int DEFAULT_MENU_DIVIDER_WIDTH = dp2Px(0.5f);
    private static final int DEFAULT_MENU_DIVIDER_MARGIN_TOP = dp2Px(0);
    private static final int DEFAULT_MENU_DIVIDER_MARGIN_BOTTOM = dp2Px(0);

    private static final int DEFAULT_DIVIDER_HEIGHT = dp2Px(0.5f);
    private static final int DEFAULT_DIVIDER_MARGIN_LEFT = dp2Px(0);
    private static final int DEFAULT_DIVIDER_MARGIN_RIGHT = dp2Px(0);

    private static final int DEFAULT_MENU_TEXT_SIZE = sp2px(14f);
    private static final int DEFAULT_MENU_HEIGHT = dp2Px(32);

    private static final int COLOR_WHITE = 0xffffffff;
    private static final int COLOR_GRAY = 0xff777777;
    private static final int COLOR_BLACK = 0xff000000;
    private static final int COLOR_MASK = 0x99000000;


    // menu分割线宽度
    private int menuDividerWidth = DEFAULT_MENU_DIVIDER_WIDTH;
    // menu分割线上边距
    private int menuDividerMarginTop = DEFAULT_MENU_DIVIDER_MARGIN_TOP;
    // menu分割线下边距
    private int menuDividerMarginBottom = DEFAULT_MENU_DIVIDER_MARGIN_BOTTOM;
    // menu分割线颜色
    private int menuDividerColor = COLOR_WHITE;


    // divider高度
    private int dividerLineHeight = DEFAULT_DIVIDER_HEIGHT;
    // divider左边距
    private int dividerMarginLeft = DEFAULT_DIVIDER_MARGIN_LEFT;
    // divider右边距
    private int dividerMarginRight = DEFAULT_DIVIDER_MARGIN_RIGHT;
    // divider背景色
    private int dividerBackgroundColor = COLOR_GRAY;


    // tab字体大小
    private int menuTextSize = DEFAULT_MENU_TEXT_SIZE;
    // 筛选menu高度
    private int menuHeight = DEFAULT_MENU_HEIGHT;
    // 最大高度百分比
    private float menuMaxHeightPercent = 0.6f;
    // 筛选menu背景色
    private int menuBackgroundColor = COLOR_WHITE;
    // tab展开时对应标题文字颜色
    private int textSelectedColor = COLOR_BLACK;
    // tab关闭时对应标题文字颜色
    private int textUnSelectedColor = COLOR_BLACK;
    // tab选中图标
    private int menuSelectedIcon;
    // tab未选中图标
    private int menuUnSelectedIcon;
    // tab标题文字和图标间距
    private int menuIconPadding = DEFAULT_ICON_PADDING;

    // 遮罩颜色
    private int maskColor = COLOR_MASK;

    /**
     * 筛选栏
     */
    private LinearLayout tabMenuView;
    /**
     * 透明遮罩栏View，点击可关闭DropDownMenu
     */
    private View maskView;
    /**
     * 弹出菜单父布局
     */
    private FrameLayout popupMenuViews;
    /**
     * tabMenuView里面选中的tab位置，-1表示未选中
     */
    private int currentTabPosition = -1;

    private OnMenuShowListener onMenuShowListener;


    public DropDownMenu(Context context) {
        this(context, null, 0);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        // menu divider
        menuDividerWidth = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_menuDividerWidth, menuDividerWidth);
        menuDividerMarginTop = typedArray.getInteger(R.styleable.DropDownMenu_menuDividerMarginTop, menuDividerMarginTop);
        menuDividerMarginBottom = typedArray.getInteger(R.styleable.DropDownMenu_menuDividerMarginBottom, menuDividerMarginBottom);
        menuDividerColor = typedArray.getColor(R.styleable.DropDownMenu_menuDividerColor, menuDividerColor);
        //divider
        dividerLineHeight = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_dividerLineHeight, dividerLineHeight);
        dividerMarginLeft = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_dividerMarginLeft, dividerMarginLeft);
        dividerMarginRight = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_dividerMarginRight, dividerMarginRight);
        dividerBackgroundColor = typedArray.getColor(R.styleable.DropDownMenu_dividerBackgroundColor, dividerBackgroundColor);
        // menu
        menuTextSize = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_menuTextSize, menuTextSize);
        menuHeight = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_menuHeight, menuHeight);
        menuMaxHeightPercent = typedArray.getFloat(R.styleable.DropDownMenu_menuMaxHeightPercent, menuMaxHeightPercent);
        menuBackgroundColor = typedArray.getColor(R.styleable.DropDownMenu_menuBackgroundColor, menuBackgroundColor);
        textSelectedColor = typedArray.getColor(R.styleable.DropDownMenu_textSelectedColor, textSelectedColor);
        textUnSelectedColor = typedArray.getColor(R.styleable.DropDownMenu_textUnSelectedColor, textUnSelectedColor);
        menuSelectedIcon = typedArray.getResourceId(R.styleable.DropDownMenu_menuSelectedIcon, menuSelectedIcon);
        menuUnSelectedIcon = typedArray.getResourceId(R.styleable.DropDownMenu_menuUnSelectedIcon, menuUnSelectedIcon);
        menuIconPadding = typedArray.getDimensionPixelSize(R.styleable.DropDownMenu_menuIconPadding, menuIconPadding);
        // mask
        maskColor = typedArray.getColor(R.styleable.DropDownMenu_maskColor, maskColor);

        createAddViews(context);

        typedArray.recycle();
    }

    private void createAddViews(Context context) {
        //标题栏
        tabMenuView = new LinearLayout(context);
        tabMenuView.setOrientation(LinearLayout.HORIZONTAL);
        tabMenuView.setBackgroundColor(menuBackgroundColor);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = menuHeight;
        tabMenuView.setLayoutParams(layoutParams);

        //分割线
        View dividerView = new View(getContext());
        dividerView.setBackgroundColor(dividerBackgroundColor);
        LayoutParams dividerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerLineHeight);
        dividerParams.setMargins(dividerMarginLeft, 0, dividerMarginRight, 0);
        dividerView.setLayoutParams(dividerParams);

        this.addView(tabMenuView, 0);
        this.addView(dividerView, 1);
    }


    /**
     * 设置筛选项
     * 可重复调用该方法，第二次会把第一次的值覆盖
     *
     * @param tabTexts   筛选标题
     * @param popupViews 筛选展开的View
     */
    public void setDropDownMenu(@NonNull List<String> tabTexts, @NonNull List<View> popupViews) {
        if (!(getParent() instanceof RelativeLayout)) {
            throw new IllegalArgumentException("Parent View must be RelativeLayout");
        }
        if (tabTexts.size() != popupViews.size()) {
            throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
        }

        if (tabMenuView != null) {
            tabMenuView.removeAllViews();
        }
        if (popupMenuViews != null) {
            popupMenuViews.removeAllViews();
        }

        //tabMenu层
        addTabs(tabTexts);

        maskView = new View(getContext());
        RelativeLayout.LayoutParams layoutParamsMV = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsMV.addRule(RelativeLayout.BELOW, this.getId());
        maskView.setLayoutParams(layoutParamsMV);
        maskView.setVisibility(GONE);
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        popupMenuViews = new FrameLayout(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (getScreenSize(getContext()).y * menuMaxHeightPercent));
        layoutParams.addRule(RelativeLayout.BELOW, this.getId());
        popupMenuViews.setLayoutParams(layoutParams);
        popupMenuViews.setVisibility(GONE);
        for (int i = 0; i < popupViews.size(); i++) {
            View view = popupViews.get(i);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            popupMenuViews.addView(view, i);
        }


        ((RelativeLayout) getParent()).addView(maskView);
        ((RelativeLayout) getParent()).addView(popupMenuViews);
    }


    private void addTabs(List<String> tabTexts) {
        for (int i = 0; i < tabTexts.size(); i++) {
            addTab(tabTexts.get(i), i == tabTexts.size() - 1);
        }
    }

    private void addTab(String text, boolean isLast) {
        TextView tab = new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tab.setTextColor(textUnSelectedColor);
        tab.setText(text);
        if (menuUnSelectedIcon != 0) {
            tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnSelectedIcon), null);
            tab.setCompoundDrawablePadding(menuIconPadding);
        }

        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(tab);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(linearLayout);
            }
        });

        tabMenuView.addView(linearLayout);
        //添加分割线，最后一个不添加
        if (!isLast) {
            View view = new View(getContext());
            LayoutParams layoutParams = new LayoutParams(dp2Px(menuDividerWidth), ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, menuDividerMarginTop, 0, menuDividerMarginBottom);
            view.setLayoutParams(layoutParams);
            view.setBackgroundColor(menuDividerColor);
            tabMenuView.addView(view);
        }
    }

    /**
     * 切换菜单
     *
     * @param target 当前的popupMenuView
     */
    private void switchMenu(View target) {
        for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2) {
            if (target == tabMenuView.getChildAt(i)) {
                if (currentTabPosition == i) {
                    closeMenu();
                } else {
                    if (currentTabPosition == -1) {
                        popupMenuViews.setVisibility(View.VISIBLE);
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_in));
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_in));
                        popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    } else {
                        popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    }
                    if (onMenuShowListener != null) {
                        onMenuShowListener.showAtPosition(i / 2);
                    }
                    currentTabPosition = i;

                    TextView tabMenuTextView = getTabMenuTextView(i);
                    if (tabMenuTextView != null && menuSelectedIcon != 0) {
                        tabMenuTextView.setTextColor(textSelectedColor);
                        tabMenuTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(menuSelectedIcon), null);
                    }

                }
            } else {
                TextView tabMenuTextView = getTabMenuTextView(i);
                if (tabMenuTextView != null) {
                    tabMenuTextView.setTextColor(textUnSelectedColor);
                    if (menuUnSelectedIcon != 0) {
                        tabMenuTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                getResources().getDrawable(menuUnSelectedIcon), null);
                    }
                }


                popupMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
            }
        }
    }


    public void setTabText(String tabText) {
        if (currentTabPosition != -1) {
            TextView tabMenuTextView = getTabMenuTextView(currentTabPosition);
            if (tabMenuTextView != null) {
                tabMenuTextView.setText(tabText);
            }
        }
    }

    /**
     * 获取TabMenu的TextView
     *
     * @param position 第几项
     * @return TextView
     */
    private TextView getTabMenuTextView(int position) {
        View childViewAt = tabMenuView.getChildAt(position);
        if (childViewAt instanceof LinearLayout) {
            View childAt = ((LinearLayout) childViewAt).getChildAt(0);
            if (childAt instanceof TextView) {
                return (TextView) childAt;
            }
        }
        return null;
    }


    /**
     * DropDownMenu是否处于可见状态
     */
    public boolean isShowing() {
        return currentTabPosition != -1;
    }

    public void closeMenu() {
        if (currentTabPosition != -1) {

            TextView tabMenuTextView = getTabMenuTextView(currentTabPosition);
            if (tabMenuTextView != null) {
                tabMenuTextView.setTextColor(textUnSelectedColor);
                if (menuUnSelectedIcon != 0) {
                    tabMenuTextView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            getResources().getDrawable(menuUnSelectedIcon), null);
                }
            }

            popupMenuViews.setVisibility(View.GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_out));
            currentTabPosition = -1;
        }
    }

    public void setOnMenuShowListener(OnMenuShowListener onMenuShowListener) {
        this.onMenuShowListener = onMenuShowListener;
    }

    public interface OnMenuShowListener {
        void showAtPosition(int position);
    }

    private static int dp2Px(float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics()) + 0.5);
    }

    private static int sp2px(float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, Resources.getSystem().getDisplayMetrics()) + 0.5);
    }

    private Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

}
