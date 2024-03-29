package ric.silva.revsample.view.customListeners

import android.os.SystemClock
import android.view.View

class SingleClickListener(
    private var defaultInterval: Int = 500,
    private val calback: (() -> Unit)?
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        calback?.invoke()
    }
}

fun View.setSingleOnClickListener(calback: (() -> Unit)?) {
    val safeClickListener = SingleClickListener(calback = calback)
    setOnClickListener(safeClickListener)
}