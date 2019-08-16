package ademar.sample.wawa

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
        Thread(this).start()
    }

    private fun applyTheme(theme: Theme) {
        toolbar.setBackgroundColor(theme.slot1)
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
