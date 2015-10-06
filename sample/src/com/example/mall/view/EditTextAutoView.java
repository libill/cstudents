package com.example.mall.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.mall.R;


/**
 * 自定义可删除输入框
 */
public class EditTextAutoView extends LinearLayout implements TextWatcher,
        OnFocusChangeListener {

    /**
     * 控件父布局
     */
    private RelativeLayout layout;
    /**
     * 显示输入字符量父布局
     */
    private LinearLayout showByteLLyt;
    /**
     * overByteTv：已输入文字量 ;totleByteTv:最大可输入文字量
     */
    private TextView overByteTv, totleByteTv, titleTv, contextTv;
    /**
     * 文本框对象
     */
    private EditText mEdiText;
    /**
     * mdelImage:删除按钮;mleftImage:左侧icon
     */
    private ImageView mdelImage, mleftImage;
    /**
     * 文本框内容变化监听器
     */
    private AutoTextWatcher tw;
    /**
     * 标记是否显示“输入字符量”
     */
    private boolean showByte = false;
    /**
     * 标记默认与获焦时icon的图片
     */
    private Drawable drawableDefault, drawableFocus;

    /**
     * 标记默认与获焦时右侧icon的图片
     */
    private Drawable rIconDrawableDefault, rIconDrawableFocus;
    /**
     * 是事禁用回车键
     */
    private boolean disableenter = false;

    /**
     * 背景Drawable
     */
    private Drawable bgBackgrounddefault, bgBackgroundfocus;
    /**
     * 背景是否不显示
     */
    private boolean isBackgroundNull = false;

    private String ellipsize = "";
    private boolean singleLine = false;
    /**
     * 是否获取焦点 默认获取
     */
    private boolean isFocus = true;
    // start middle marquee
    private final String ELLIPSIZE_VALUE_END = "end";

    private final String ELLIPSIZE_VALUE_START = "start";
    private final String ELLIPSIZE_VALUE_MIDDLE = "middle";
    private AutoOnFocusChangeListener fc;
    private Context mContext;

    private String hint;

    private boolean ellipsizeValid = true;

    private boolean showRightIcon = true;

    private boolean showLeftIcon = true;

    public final String RIGHT_ICON_TYPE_DELETE = "delete";
    public final String RIGHT_ICON_TYPE_OTHER = "other";
    /**
     * 右侧按钮类型
     */
    private String rightIconType = RIGHT_ICON_TYPE_DELETE;

    /**
     * 文字大小
     */
    private float textSize;

    private int mIconLeftMargin;
    private int mIconRightMargin;

    private AutoOnClickListener onClickListener;

    public EditTextAutoView(Context context) {
        super(context);
        this.mContext = context;
        initView();
        setEvent();
    }

    public EditTextAutoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        setEditTextAttrs(context, attrs);
        setEvent();
    }

    public EditTextAutoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        setEditTextAttrs(context, attrs);
        setEvent();
    }

    /**
     * 7大属性初始化 并设置值
     *
     * @param context
     * @param attrs
     * @returnType void
     */
    private void setEditTextAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CommonEditTextView);
        Resources res = context.getResources();
        bgBackgrounddefault = context.getResources().getDrawable(
                R.drawable.edittext_default);
        bgBackgroundfocus = context.getResources().getDrawable(
                R.drawable.edittext_focused);

        Drawable tempRIconDrawableDefault = context.getResources().getDrawable(
                R.drawable.icon_del_default);
        Drawable tempRIconDrawableFocus = context.getResources().getDrawable(
                R.drawable.icon_del_default);

        // 背景是否显示
        isBackgroundNull = array.getBoolean(
                R.styleable.CommonEditTextView_isBackgroundNull, false);
        if (!isBackgroundNull) {
            layout.setBackgroundDrawable(bgBackgrounddefault);
            // 背景
            bgBackgrounddefault = array
                    .getDrawable(R.styleable.CommonEditTextView_bgBackgrounddefault);
            bgBackgroundfocus = array
                    .getDrawable(R.styleable.CommonEditTextView_bgBackgroundfocus);
            if (bgBackgrounddefault == null) {
                bgBackgrounddefault = context.getResources().getDrawable(
                        R.drawable.edittext_default);
            }
            if (bgBackgroundfocus == null) {
                bgBackgroundfocus = context.getResources().getDrawable(
                        R.drawable.edittext_focused);
            }
        }

        // 回车处理
        disableenter = array.getBoolean(
                R.styleable.CommonEditTextView_disableenter, disableenter);

        String inputType = array
                .getString(R.styleable.CommonEditTextView_inputType);
        // 配置左侧小图标
        cfgLeftIcon(array, res);

        // 左侧小图标
        drawableDefault = array
                .getDrawable(R.styleable.CommonEditTextView_drawableLeftdefault);
        drawableFocus = array
                .getDrawable(R.styleable.CommonEditTextView_drawableLeftfocus);

        isShowLeftIcon();

        // 最大输入值
        int maxLength = array.getInt(R.styleable.CommonEditTextView_maxLength,
                100);
        mEdiText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(
                maxLength)});

        // 内容
        String text = array.getString(R.styleable.CommonEditTextView_text);
        if (text != null) {
            mEdiText.setText("" + text);
        } else {
            mEdiText.setText("");
        }

        // 提示
        hint = array.getString(R.styleable.CommonEditTextView_hint);
        if (hint != null) {
            mEdiText.setHint("" + hint);
        } else {
            mEdiText.setHint("");
        }

        // 标题
        String title = array.getString(R.styleable.CommonEditTextView_etTitle);
        if (title != null && !"".equals(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
            if (drawableDefault != null) {
                drawableDefault = null;
                drawableFocus = null;
                isShowLeftIcon();
            }
        }

        String digits = array.getString(R.styleable.CommonEditTextView_digits);
        if (!TextUtils.isEmpty(digits)) {
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new MyInputFilter(digits);
            mEdiText.setFilters(filters);
        }

        // 输入文字类型
        if ("number".equals(inputType)) {
            mEdiText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if ("phone".equals(inputType)) {
            mEdiText.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if ("textPassword".equals(inputType)) {
            mEdiText.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            mEdiText.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        }

        showByte = array.getBoolean(R.styleable.CommonEditTextView_showBytes,
                false);
        // 是否显示可输入字符提示
        if (showByte) {
            mdelImage.setVisibility(View.GONE);
            showByteLLyt.setVisibility(View.VISIBLE);
            totleByteTv.setText("/" + maxLength);
            overByteTv.setText("" + mEdiText.getText().toString().length());
        } else {
            mdelImage.setVisibility(View.INVISIBLE);
            if ((showRightIcon && !RIGHT_ICON_TYPE_DELETE.equals(rightIconType))
                    || (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)
                    && mEdiText.isFocused()
                    && mEdiText.getText().toString().trim().length() > 0 && showRightIcon)) {
                mdelImage.setVisibility(View.VISIBLE);
            }
            showByteLLyt.setVisibility(View.GONE);
        }

        // 是否单行
        singleLine = array.getBoolean(
                R.styleable.CommonEditTextView_singleLine, false);
        if (singleLine) {
            mEdiText.setSingleLine(true);
            ellipsize = array
                    .getString(R.styleable.CommonEditTextView_ellipsize);
            if (ellipsize == null) {
                ellipsize = "none";
            }
        }

        if (!isFocus) {
            mEdiText.setFocusable(true);
            mEdiText.setFocusableInTouchMode(true);

        }

        // 右侧icon处理
        String tempRightIconType = array
                .getString(R.styleable.CommonEditTextView_rightIconType);
        rightIconType = TextUtils.isEmpty(tempRightIconType) ? rightIconType
                : tempRightIconType;

        rIconDrawableDefault = array
                .getDrawable(R.styleable.CommonEditTextView_drawableRightdefault);
        rIconDrawableFocus = array
                .getDrawable(R.styleable.CommonEditTextView_drawableRightfocus);

        rIconDrawableDefault = rIconDrawableDefault == null ? tempRIconDrawableDefault
                : rIconDrawableDefault;
        rIconDrawableFocus = rIconDrawableFocus == null ? tempRIconDrawableFocus
                : rIconDrawableFocus;

        isShowRightIcon();

        array.recycle();
    }

    private void cfgLeftIcon(TypedArray array, Resources res) {
        boolean isCfg = array
                .hasValue(R.styleable.CommonEditTextView_iconLeftMargin);
        if (isCfg) {
            mIconLeftMargin = array.getDimensionPixelOffset(
                    R.styleable.CommonEditTextView_iconLeftMargin,
                    res.getDimensionPixelOffset(R.dimen.px_hdpi_16));
        } else {
            return;
        }

        mIconRightMargin = array.getDimensionPixelOffset(
                R.styleable.CommonEditTextView_iconRightMargin,
                res.getDimensionPixelOffset(R.dimen.px_hdpi_32));
        ViewGroup.LayoutParams lpLeftImage = mleftImage.getLayoutParams();
        LayoutParams cfgLayoutParam = new LayoutParams(lpLeftImage);
        cfgLayoutParam.leftMargin = mIconLeftMargin;
        cfgLayoutParam.rightMargin = mIconRightMargin;
        mleftImage.setLayoutParams(cfgLayoutParam);

        mleftImage.requestLayout();
    }

    public class MyInputFilter extends UsernameFilterGeneric {
        private String mAllowedDigits;

        public MyInputFilter(String digits) {
            mAllowedDigits = digits;
        }

        @Override
        public boolean isAllowed(char c) {
            if (mAllowedDigits.indexOf(c) != -1) {
                return true;
            }
            return false;
        }
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_edittext_auto_view,
                null);
        layout = (RelativeLayout) view.findViewById(R.id.rl_root);
        showByteLLyt = (LinearLayout) view
                .findViewById(R.id.show_byte_number_llyt);
        mEdiText = (EditText) view.findViewById(R.id.common_edittext);
        mdelImage = (ImageView) view.findViewById(R.id.iv_delete);
        mleftImage = (ImageView) view.findViewById(R.id.iv_left_icon);
        overByteTv = (TextView) view.findViewById(R.id.over_byte);
        totleByteTv = (TextView) view.findViewById(R.id.totle_byte);
        contextTv = (TextView) view.findViewById(R.id.common_edittext_Tv);
        titleTv = (TextView) view.findViewById(R.id.tv_title);
        mdelImage.setVisibility(View.GONE);
        addView(view);

    }

    private void setEvent() {
        mEdiText.addTextChangedListener(this);
        mEdiText.setOnFocusChangeListener(this);
        mdelImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)) {
                    mEdiText.setText("");
                }

                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });
        layout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (singleLine) {
                    contextTv.setVisibility(View.GONE);
                    mEdiText.setVisibility(View.VISIBLE);
                    mEdiText.setSelection(mEdiText.getText().toString()
                            .length());
                    /* mEdiText.setEllipsize(TruncateAt.valueOf("END")); */
                }
                mEdiText.setFocusable(true);
                mEdiText.setFocusableInTouchMode(true);
                mEdiText.requestFocus();
                mEdiText.requestFocusFromTouch();
            }
        });

        mEdiText.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (disableenter) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        return true;
                    }
                }
                return false;
            }
        });

    }

    public EditText getmEdiText() {
        return mEdiText;
    }

    /**
     * 根据用户左侧icon属性设置值，判断左侧icon是否显示
     *
     * @returnType void
     */
    @SuppressWarnings("deprecation")
    private void isShowLeftIcon() {
        if (drawableDefault != null && showLeftIcon) {
            mleftImage.setBackgroundDrawable(drawableDefault);
            mleftImage.setVisibility(View.VISIBLE);
        } else {
            mleftImage.setVisibility(View.GONE);
        }

    }

    /**
     * 根据用户左侧icon属性设置值，判断左侧icon是否显示
     *
     * @returnType void
     */
    @SuppressWarnings("deprecation")
    private void isShowRightIcon() {
        if (rIconDrawableDefault != null) {
            mdelImage.setBackgroundDrawable(rIconDrawableDefault);
        }

        if ((showRightIcon && !RIGHT_ICON_TYPE_DELETE.equals(rightIconType))
                || (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)
                && mEdiText.isFocused()
                && mEdiText.getText().toString().trim().length() > 0 && showRightIcon)) {
            mdelImage.setVisibility(View.VISIBLE);
        } else {
            mdelImage.setVisibility(View.GONE);
        }

    }

    /**
     * 文本框内容变化前 针对“显示输入字符量 ”或“删除按钮”做监听
     *
     * @param s
     * @see TextWatcher#afterTextChanged(Editable)
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (showByte) {
            overByteTv.setText("" + mEdiText.getText().toString().length());
        } else {
            if (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)) {
                if (s.length() > 0 && mEdiText.isFocused() && !showByte
                        && showRightIcon) {
                    mdelImage.setVisibility(View.VISIBLE);
                } else {
                    mdelImage.setVisibility(View.INVISIBLE);
                }
            }
        }
        if (tw != null) {
            tw.afterTextChanged(s);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        if (tw != null) {
            tw.beforeTextChanged(s, start, count, after);
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (tw != null) {
            tw.onTextChanged(s, start, before, count);
        }

    }

    /**
     * 文本框获焦处理 1：文本框背景 2：左侧icon
     *
     * @param arg0
     * @param hasFocus
     * @see OnFocusChangeListener#onFocusChange(View,
     * boolean)
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onFocusChange(View arg0, boolean hasFocus) {
        if (hasFocus) {
            if (!isBackgroundNull) {
                layout.setBackgroundDrawable(bgBackgroundfocus);
            }
            if (mEdiText.getText().toString().length() > 0 && !showByte
                    && showRightIcon
                    && RIGHT_ICON_TYPE_DELETE.equals(rightIconType)) {
                mdelImage.setVisibility(View.VISIBLE);
            }
            if (drawableFocus != null) {
                mleftImage.setBackgroundDrawable(drawableFocus);
            }
        } else {
            if (!isBackgroundNull) {
                layout.setBackgroundDrawable(bgBackgrounddefault);
            }
            if (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)) {
                mdelImage.setVisibility(View.INVISIBLE);
            }
            if (drawableFocus != null) {
                mleftImage.setBackgroundDrawable(drawableDefault);
            }
            if (singleLine) {
                contextTv.setText(mEdiText.getText().toString());
                if (ELLIPSIZE_VALUE_END.equalsIgnoreCase(ellipsize)) {
                    contextTv.setEllipsize(TruncateAt.END);
                } else if (ELLIPSIZE_VALUE_START.equalsIgnoreCase(ellipsize)) {
                    contextTv.setEllipsize(TruncateAt.START);
                } else if (ELLIPSIZE_VALUE_MIDDLE.equalsIgnoreCase(ellipsize)) {
                    contextTv.setEllipsize(TruncateAt.MIDDLE);
                }
                contextTv.setVisibility(View.VISIBLE);
                mEdiText.setVisibility(View.GONE);
                /* mEdiText.setEllipsize(TruncateAt.valueOf("END")); */
            }
        }

        if (fc != null) {
            fc.onFocusChange(arg0, hasFocus);
        }
    }

    /**
     * 自定义文本值变化监听器
     */
    public interface AutoTextWatcher {
        public void afterTextChanged(Editable s);

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after);

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count);
    }

    public interface AutoOnFocusChangeListener {
        public void onFocusChange(View arg0, boolean hasFocus);
    }

    public interface AutoOnClickListener {
        public void onClick(View v);
    }

    /**
     * 获取文本值内容 左右除空
     *
     * @return
     * @returnType String
     */
    public String getText() {
        return mEdiText.getText().toString().trim();
    }

    /**
     * 设置文本值 并设置ellipsize是否有效 (false 无效)
     *
     * @param value
     * @returnType void
     */
    public void setText(String value, boolean isEllipsizeValid) {
        this.ellipsizeValid = isEllipsizeValid;
        setText(value);
    }

    /**
     * 设置文本值
     *
     * @param value
     * @returnType void
     */
    public void setText(String value) {
        contextTv.setText(value);
        mEdiText.setText(value);
        if (singleLine && ellipsizeValid) {
            if (TextUtils.isEmpty(value)) {
                contextTv.setText(hint);
                contextTv.setTextColor(mContext.getResources().getColor(
                        R.color.gray));
            } else {
                contextTv.setTextColor(mContext.getResources().getColor(
                        R.color.black_light));
            }

            if (ELLIPSIZE_VALUE_END.equalsIgnoreCase(ellipsize)) {
                contextTv.setEllipsize(TruncateAt.END);
            } else if (ELLIPSIZE_VALUE_START.equalsIgnoreCase(ellipsize)) {
                contextTv.setEllipsize(TruncateAt.START);
            } else if (ELLIPSIZE_VALUE_MIDDLE.equalsIgnoreCase(ellipsize)) {
                contextTv.setEllipsize(TruncateAt.MIDDLE);
            }
            mEdiText.setVisibility(View.GONE);
            contextTv.setVisibility(View.VISIBLE);
        } else {
            mEdiText.setVisibility(View.VISIBLE);
            contextTv.setVisibility(View.GONE);
        }
    }

    /**
     * 设置提示语
     *
     * @param hint
     * @returnType void
     */
    public void setHint(String hint) {
        mEdiText.setHint(hint);
    }

    /**
     * 获取提示语
     *
     * @returnType String
     */
    public String getHint() {
        return mEdiText.getHint().toString().trim();
    }

    public void setRightIconDrawable(Drawable rIconDrawableDefault,
                                     Drawable rIconDrawableFocus) {
        this.rIconDrawableDefault = rIconDrawableDefault;
        this.rIconDrawableFocus = rIconDrawableFocus;
        isShowRightIcon();
    }

    public void setRightIconType(String type) {
        mdelImage.setVisibility(View.GONE);
        rightIconType = TextUtils.isEmpty(type) ? RIGHT_ICON_TYPE_DELETE
                : type;
        if (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)
                || mContext.getResources().getDrawable(
                R.drawable.icon_del_default) == rIconDrawableDefault) {
            // 删除类型处理
            if ((showRightIcon && !RIGHT_ICON_TYPE_DELETE.equals(rightIconType))
                    || (RIGHT_ICON_TYPE_DELETE.equals(rightIconType)
                    && mEdiText.isFocused()
                    && mEdiText.getText().toString().trim().length() > 0 && showRightIcon)) {
                mdelImage.setVisibility(View.VISIBLE);
            }

        } else {
            // 其它按扭处理
            if (showRightIcon) {
                mdelImage.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置字体大小
     *
     */
    public void setTextSize(int size) {
        mEdiText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    /**
     * 设置文本框值变化监听器
     *
     * @param tw
     * @returnType void
     */
    public void setAutoTextWatcher(AutoTextWatcher tw) {
        this.tw = tw;
    }

    /**
     * 设置文本框焦点监听器
     *
     * @param fc
     * @returnType void
     */
    public void setAutoOnFocusChangeListener(AutoOnFocusChangeListener fc) {
        this.fc = fc;
    }

    public void setAutoOnClickListener(AutoOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 获取文本框对象
     *
     * @return
     * @returnType EditText
     */
    public EditText getEditText() {
        return mEdiText;
    }

    /**
     * 是否显示右侧icon
     *
     * @param showIcon
     * @returnType void
     */
    public void showRightIcon(boolean showIcon) {
        this.showRightIcon = showIcon;
        isShowRightIcon();
    }

    public void showLeftIcon(boolean showIcon) {
        this.showLeftIcon = showIcon;
        int show = showIcon ? View.VISIBLE : View.GONE;
        mleftImage.setVisibility(show);

    }

}
