package ademar.sample.wawa

import android.graphics.Color
import com.google.gson.annotations.SerializedName

data class ThemePayload(

    @SerializedName("slot_1")
    val slot1: String?,

    @SerializedName("slot_2")
    val slot2: String?

) {

    fun asTheme(): Theme? {
        val slot1Color = slot1.asColor()
        val slot2Color = slot2.asColor()
        return if (
            slot1Color == null ||
            slot2Color == null
        ) null else Theme(
            slot1Color,
            slot2Color
        )
    }

}

data class Theme(
    val slot1: Int = Color.TRANSPARENT,
    val slot2: Int = Color.TRANSPARENT
)

fun String?.asColor(): Int? {
    if (this == null) return null
    return try {
        Color.parseColor(this)
    } catch (t: Throwable) {
        null
    }
}
