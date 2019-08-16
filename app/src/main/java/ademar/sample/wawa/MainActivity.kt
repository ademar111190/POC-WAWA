package ademar.sample.wawa

import android.graphics.Color
import android.graphics.PorterDuff.Mode.MULTIPLY
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), Runnable {

    private var theme = Theme()
        set(value) {
            Log.d("WAWA", "Setting from $field to $value")
            field = value
            container.post {
                applyTheme(field)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (SDK_INT >= JELLY_BEAN) {
            window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (SDK_INT >= LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            appbar.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                status.elevation = appbar.elevation
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(status) { _, insets ->
            status.layoutParams = status.layoutParams.apply {
                height = insets.systemWindowInsetTop
            }
            toolbar.layoutParams = (toolbar.layoutParams as ViewGroup.MarginLayoutParams).apply {
                setMargins(leftMargin, insets.systemWindowInsetTop, rightMargin, bottomMargin)
            }
            insets
        }

        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.menu)

        Thread(this).start()
    }

    private fun applyTheme(theme: Theme) {
        with(theme.slot1) {
            appbar.setBackgroundColor(this)
            status.setBackgroundColor(this)
            toolbar.setBackgroundColor(this)
        }
        with(theme.slot2) {
            container.setBackgroundColor(this)
            toolbar.setTitleTextColor(this)
            toolbar.setIconColor(this)
        }
        with(theme.slot3) {
            load.indeterminateDrawable.setColorFilter(this, MULTIPLY)
            if (SDK_INT >= JELLY_BEAN) toggle.thumbDrawable.setColorFilter(this, MULTIPLY)
            for (radio in listOf(radio1, radio2)) {
                radio.highlightColor = this
                radio.setTextColor(this)
            }
            button1.background.setColorFilter(this, MULTIPLY)
            button2.setTextColor(this)
        }
        with(theme.slot4) {
            if (SDK_INT >= JELLY_BEAN) toggle.trackDrawable.setColorFilter(this, MULTIPLY)
            if (SDK_INT >= LOLLIPOP) for (radio in listOf(radio1, radio2)) radio.buttonTintList = this.asColorStateList()
        }
        if (SDK_INT >= M) {
            window.decorView.systemUiVisibility = if (ColorUtils.calculateLuminance(theme.slot1) > 0.5f)
                window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else
                window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    override fun run() {
        Requester.request({
            if (theme != it) theme = it
        }, {
            Log.e("WAWA", it.message)
        })
        Thread.sleep(100L)
        run()
    }

}
