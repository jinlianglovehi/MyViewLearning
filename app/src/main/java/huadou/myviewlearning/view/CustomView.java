package huadou.myviewlearning.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jinliang on 16-10-20.
 */
public class CustomView extends View {

    private Paint paint;
    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置抗锯齿

//        setColor()设置画笔颜色
//        setStrokeWidth()设置描边线条
//        setStyle()设置画笔的样式：
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
