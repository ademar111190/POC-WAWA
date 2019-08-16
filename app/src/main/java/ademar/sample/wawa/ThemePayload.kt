package ademar.sample.wawa

import android.graphics.Color
import com.google.gson.annotations.SerializedName

data class ThemePayload(

        @SerializedName("slot_1")
        val slot1: String?,

        @SerializedName("slot_2")
        val slot2: String?,

        @SerializedName("slot_3")
        val slot3: String?,

        @SerializedName("slot_4")
        val slot4: String?

) {

    fun asTheme(): Theme? {
        val slot1Color = slot1.asColor()
        val slot2Color = slot2.asColor()
        val slot3Color = slot3.asColor()
        val slot4Color = slot4.asColor()
        return if (
                slot1Color == null ||
                slot2Color == null ||
                slot3Color == null ||
                slot4Color == null
        ) null else Theme(
                slot1Color,
                slot2Color,
                slot3Color,
                slot4Color
        )
    }

}

data class Theme(
        val slot1: Int = Color.TRANSPARENT,
        val slot2: Int = Color.TRANSPARENT,
        val slot3: Int = Color.TRANSPARENT,
        val slot4: Int = Color.TRANSPARENT
)
