package ademar.sample.wawa

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Service {

    @GET("theme")
    fun listRepos(): Call<ThemePayload>

}

object Requester {

    private val service = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

    fun request(
        success: (Theme) -> Unit,
        error: (Throwable) -> Unit
    ) = try {
        val theme = service.listRepos().execute().body()?.asTheme()
        if (theme != null) success(theme) else error(Exception("Body is null"))
    } catch (t: Throwable) {
        error(t)
    }

}
