package pfhb.damian.drawandsave

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Typeface.NORMAL
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import pfhb.damian.drawandsave.MainActivity.Companion.paintBrush
import pfhb.damian.drawandsave.MainActivity.Companion.path
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.absoluteValue

class PaintView : View {

    var params : ViewGroup.LayoutParams? = null
    var skipper : Int = 0
    var oldX = 0F
    var oldY = 0F
    companion object{
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLUE
    }
    constructor(context: Context) : this(context, null){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        paintBrush.isAntiAlias = false

        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.MITER
        paintBrush.strokeWidth = 0.1f
        paintBrush.maskFilter = null
        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        update()
    }

    private fun update(){

        val runnable= Runnable{
            Looper.prepare()
            invalidate()
            Thread.sleep(15)
            Looper.loop()
        }
        Thread(runnable).start()
        //update()

    }


    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y
        if(abs(x-oldX) < 5 || abs(x-oldY) < 5) return false
        oldX = x
        oldY = y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else -> return false
        }

        return false
    }

    override fun onDraw(canvas: Canvas) {
        for(i in pathList.indices){
            //paintBrush.color = colorList[i]
            paintBrush.color = Color.BLUE
            canvas.drawPath(pathList[i], paintBrush)

        }
//        update()

    }
}