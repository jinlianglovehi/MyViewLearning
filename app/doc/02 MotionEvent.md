
#### 滑动效果的测试

> 基础知识：

(1) 所有 Touch 事件都被封装成了 MotionEvent 对象，包括 Touch 的位置、时间、历史记录以及第几个手指(多指触摸)等。

(2) 事件类型分为
       ACTION_DOWN, ACTION_UP,
       ACTION_MOVE, ACTION_POINTER_DOWN,
       ACTION_POINTER_UP, ACTION_CANCEL，
       每个事件都是以 ACTION_DOWN 开始 ACTION_UP 结束。

(3) 对事件的处理包括三类，分别为
       传递——dispatchTouchEvent()函数、
       拦截——onInterceptTouchEvent()函数、
       消费——onTouchEvent()函数和 OnTouchListener

>  事件传递

(1) 事件从 Activity.dispatchTouchEvent()开始传递，只要没有被停止或拦截，从最上层的 View(ViewGroup)开始一直往下(子 View)传递。子 View 可以通过 onTouchEvent()对事件进行处理。

(2) 事件由父 View(ViewGroup)传递给子 View，ViewGroup 可以通过 onInterceptTouchEvent()对事件做拦截，停止其往下传递。

(3) 如果事件从上往下传递过程中一直没有被停止，且最底层子 View 没有消费事件，事件会反向往上传递，这时父 View(ViewGroup)可以进行消费，如果还是没有被消费的话，最后会到 Activity 的 onTouchEvent()函数。

(4) 如果 View 没有对 ACTION_DOWN 进行消费，之后的其他事件不会传递过来。

(5) OnTouchListener 优先于 onTouchEvent()对事件进行消费。

上面的消费即表示相应函数返回值为 true。

#### 测量 view 的大小

Q: 为什么要测量View大小？

A: View的大小不仅由自身所决定，同时也会受到父控件的影响，为了我们的控件能更好的适应各种情况，一般会自己进行测量。

测量View大小使用的是onMeasure函数，我们可以从onMeasure的两个参数中取出宽高的相关数据：

```
   @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
        int widthmode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式

        int heightsize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
        int heightmode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
    }

```


从上面可以看出 onMeasure 函数中有 widthMeasureSpec 和 heightMeasureSpec 这两个 int 类型的参数， 毫无疑问他们是和宽高相关的， 但它们其实不是宽和高， 而是由宽、高和各自方向上对应的测量模式来合成的一个值：

```
测量模式一共有三种， 被定义在 Android 中的 View 类的一个内部类View.MeasureSpec中：
模式 	二进制数值 	描述
UNSPECIFIED 	00 	默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
EXACTLY 	01 	表示父控件已经确切的指定了子View的大小。
AT_MOST 	10 	表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。

```

在int类型的32位二进制位中，31-30这两位表示测量模式,29~0这三十位表示宽和高的实际值，实际上如下：

```
以数值1080(二进制为: 1111011000)为例(其中模式和实际数值是连在一起的，为了展示我将他们分开了)：
模式名称 	模式数值 	实际数值
UNSPECIFIED 	00 	000000000000000000001111011000
EXACTLY 	01 	000000000000000000001111011000
AT_MOST 	10 	000000000000000000001111011000

```

PS: 实际上关于上面的东西了解即可，在实际运用之中只需要记住有三种模式，用 MeasureSpec 的 getSize是获取数值， getMode是获取模式即可。
注意：


如果对View的宽高进行修改了，不要调用super.onMeasure(widthMeasureSpec,heightMeasureSpec);要调用setMeasuredDimension(widthsize,heightsize); 这个函数。

#### 自定义View流程：
步骤 	关键字 	作用
1 	构造函数 	View初始化
2 	onMeasure 	测量View大小
3 	onSizeChanged 	确定View大小
4 	onLayout 	确定子View布局(自定义View包含子View时有用)
5 	onDraw 	实际绘制内容
6 	提供接口 	控制View或监听View某些状态。