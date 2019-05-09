package hu.stephon.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 实现斜切效果的imageview
 */
public class ObliqueCutImageView extends AppCompatImageView {
    //对边切割长度
    /**
     * 对边切割长度
     *            ->v<-如 这里的长度
     * -----------....
     * |          \  .
     * |           \ .
     * |            \.
     * --------------- 方向:rightBottom
     */
    private int mExtent;
    //起始角
    private int mDiretion;
    //切割的水平垂直方向
    private int mOrientation;

    public static final int LEFT_TOP = 1;
    public static final int LEFT_BOTTOM = 2;
    public static final int RIGHT_TOP = 3;
    public static final int RIGHT_BOTTOM = 4;

    public static final int VERTICAL = 1;
    public static final int HORIZANTAL = 2;

    private Path mClipPath;

    public ObliqueCutImageView(Context context) {
        this(context, null);
    }

    public ObliqueCutImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObliqueCutImageView(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ObliqueCutImageView, 0, 0);
        mDiretion = ta.getInt(R.styleable.ObliqueCutImageView_diretion,LEFT_TOP);
        mExtent = ta.getDimensionPixelSize(R.styleable.ObliqueCutImageView_extent,0);
        mOrientation = ta.getInt(R.styleable.ObliqueCutImageView_orientation,VERTICAL);

        mClipPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        //切割距离不超过 宽度
        if (mOrientation == VERTICAL){
            if (mExtent > w){
                mExtent = w;
            }
        }else{
            if (mExtent > h){
                mExtent = h;
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mOrientation == VERTICAL){
            createClipPathVertical();
        }else{
            createClipPathHorizantal();
        }
        mClipPath.close();
        canvas.clipPath(mClipPath);
        canvas.drawColor(Color.parseColor("#00000000"));
        super.onDraw(canvas);
    }
    /**
     *水方向切割 from Left to Right
     */
    private void createClipPathHorizantal() {
        switch (mDiretion) {
            case LEFT_TOP:
                mClipPath.moveTo(0, 0);
                mClipPath.lineTo(getMeasuredWidth(), mExtent);
                mClipPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
                mClipPath.lineTo(0, getMeasuredHeight());
                mClipPath.lineTo(0, 0);
                break;
            case LEFT_BOTTOM:
                mClipPath.moveTo(0, getMeasuredHeight());
                mClipPath.lineTo(0, 0);
                mClipPath.lineTo(getMeasuredWidth(), 0);
                mClipPath.lineTo(getMeasuredWidth(), getMeasuredHeight() - mExtent);
                mClipPath.lineTo(0, getMeasuredHeight());
                break;
            case RIGHT_BOTTOM:
                mClipPath.moveTo(getMeasuredWidth(), getMeasuredHeight());
                mClipPath.lineTo(0, getMeasuredHeight() - mExtent);
                mClipPath.lineTo(0, 0);
                mClipPath.lineTo(getMeasuredWidth(), 0);
                mClipPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
                break;
            case RIGHT_TOP:
                mClipPath.moveTo(getMeasuredWidth(), 0);
                mClipPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
                mClipPath.lineTo(0, getMeasuredHeight());
                mClipPath.lineTo(0, mExtent);
                mClipPath.lineTo(getMeasuredWidth(), 0);
                break;
        }
    }

    /**
     *垂直方向切割 from Top to Bottom
     */
    private void createClipPathVertical(){
        switch (mDiretion){
            case LEFT_TOP:
                mClipPath.moveTo(0,0);
                mClipPath.lineTo(getMeasuredWidth()  , 0);
                mClipPath.lineTo(getMeasuredWidth() ,getMeasuredHeight());
                mClipPath.lineTo(mExtent,getMeasuredHeight());
                mClipPath.lineTo(0,0);
                break;
            case LEFT_BOTTOM:
                mClipPath.moveTo(0,getMeasuredHeight());
                mClipPath.lineTo(mExtent,0);
                mClipPath.lineTo(getMeasuredWidth() ,0);
                mClipPath.lineTo(getMeasuredWidth() ,getMeasuredHeight());
                mClipPath.lineTo(0,getMeasuredHeight());
                break;
            case RIGHT_BOTTOM:
                mClipPath.moveTo(getMeasuredWidth() ,getMeasuredHeight());
                mClipPath.lineTo(0,getMeasuredHeight());
                mClipPath.lineTo(0,0);
                mClipPath.lineTo(getMeasuredWidth() - mExtent ,0);
                mClipPath.lineTo(getMeasuredWidth() ,getMeasuredHeight());
                break;
            case RIGHT_TOP:
                mClipPath.moveTo(getMeasuredWidth() ,0);
                mClipPath.lineTo(getMeasuredWidth()  - mExtent,getMeasuredHeight());
                mClipPath.lineTo(0,getMeasuredHeight());
                mClipPath.lineTo(0,0);
                mClipPath.lineTo(getMeasuredWidth() ,0);
                break;
        }
    }
}
