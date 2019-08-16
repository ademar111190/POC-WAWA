package ademar.sample.wawa

import android.animation.AnimatorSet
import android.animation.ObjectAnimator.ofArgb
import android.graphics.Color
import android.graphics.PorterDuff.Mode.MULTIPLY
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import android.view.View.*
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), Runnable {

    private var theme = Theme()
        set(value) {
            runOnUiThread {
                AnimatorSet().also {
                    it.playTogether(
                            ofArgb(this, "slot1", field.slot1, value.slot1),
                            ofArgb(this, "slot2", field.slot2, value.slot2),
                            ofArgb(this, "slot3", field.slot3, value.slot3),
                            ofArgb(this, "slot4", field.slot4, value.slot4)
                    )
                    it.duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
                }.start()
                field = value
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        appbar.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            status.elevation = appbar.elevation
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

    @Keep
    fun setSlot1(color: Int) {
        appbar.setBackgroundColor(color)
        status.setBackgroundColor(color)
        toolbar.setBackgroundColor(color)
        for (edit_text in listOf(edit_text1, edit_text2)) edit_text.setTextColor(color)

        if (SDK_INT >= M) {
            window.decorView.systemUiVisibility = if (ColorUtils.calculateLuminance(color) > 0.5f)
                window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else
                window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    @Keep
    fun setSlot2(color: Int) {
        container.setBackgroundColor(color)
        toolbar.setTitleTextColor(color)
        toolbar.setIconColor(color)
    }

    @Keep
    fun setSlot3(color: Int) {
        load.indeterminateDrawable.setColorFilter(color, MULTIPLY)
        toggle.thumbDrawable.setColorFilter(color, MULTIPLY)
        for (radio in listOf(radio1, radio2)) {
            radio.highlightColor = color
            radio.setTextColor(color)
        }
        button1.background.setColorFilter(color, MULTIPLY)
        button2.setTextColor(color)
    }

    @Keep
    fun setSlot4(color: Int) {
        toggle.trackDrawable.setColorFilter(color, MULTIPLY)
        for (radio in listOf(radio1, radio2)) radio.buttonTintList = color.asColorStateList()
        for (input in listOf(input1, input2)) input.defaultHintTextColor = color.asColorStateList()
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
