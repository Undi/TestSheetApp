package czajkowski.patryk.testsheetapp.home.repository.api.factory

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {

    fun createGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

}