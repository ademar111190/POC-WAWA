package ademar.sample.wawa

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff.Mode.MULTIPLY
import android.graphics.PorterDuffColorFilter
import android.widget.ImageButton
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.ActionMenuView
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.activity_login.view.*

fun String?.asColor(): Int? {
    if (this == null) return null
    return try {
        Color.parseColor(this)
    } catch (t: Throwable) {
        null
    }
}

fun Int.asColorStateList() = ColorStateList.valueOf(this)

fun Toolbar.setIconColor(color: Int) {
    val colorFilter = PorterDuffColorFilter(color, MULTIPLY)
    for (i in 0 until toolbar.childCount) {
        val view = toolbar.getChildAt(i)
        if (view is ImageButton) view.drawable.colorFilter = colorFilter
        else if (view is ActionMenuView) {
            for (j in 0 until view.childCount) {
                val innerView = view.getChildAt(j)
                if (innerView is ActionMenuItemView) {
                    val drawables = innerView.compoundDrawables
                    for (k in drawables.indices) {
                        drawables[k]?.let { DrawableCompat.setTint(it, color) }
                    }
                }
            }
        }
    }
    val overflowIcon = toolbar.overflowIcon
    if (overflowIcon != null) {
        overflowIcon.colorFilter = colorFilter
        toolbar.overflowIcon = overflowIcon
    }
}
