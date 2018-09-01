package scut.carson_ho.searchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/9/1 16:31
 * Description: DrawableComponent居中的TextView
 * Remarks: 参考：https://blog.csdn.net/wk843620202/article/details/59173899
 * =======================================================
 */
public class DrawableCenterTextView extends android.support.v7.widget.AppCompatTextView {
    public DrawableCenterTextView(Context context) {
        this(context, null);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable[] drawables = getCompoundDrawables();
        Drawable leftDrawable = drawables[0]; //drawableLeft
        Drawable rightDrawable = drawables[2];//drawableRight
        if (leftDrawable != null || rightDrawable != null) {
            // 获取文字宽度
            float textWidth = getPaint().measureText(getText().toString());
            // 获取 padding
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth;
            float bodyWidth;
            if (leftDrawable != null) {
                // 获取drawable的宽度
                drawableWidth = leftDrawable.getIntrinsicWidth();
                // 获取绘制区域的总宽度
                bodyWidth = textWidth + drawablePadding + drawableWidth;
            } else {
                drawableWidth = rightDrawable.getIntrinsicWidth();
                bodyWidth = textWidth + drawablePadding + drawableWidth;
                // 图片居右设置 padding
                setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
            }
            canvas.translate((getWidth() - bodyWidth) / 2, 0);
        }
        super.onDraw(canvas);
    }
}
