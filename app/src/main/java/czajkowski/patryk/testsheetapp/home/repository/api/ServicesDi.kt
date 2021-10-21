package czajkowski.patryk.testsheetapp.home.repository.api

import czajkowski.patryk.testsheetapp.home.repository.api.factory.GsonFactory
import czajkowski.patryk.testsheetapp.home.repository.api.factory.OkHttpClientFactory
import czajkowski.patryk.testsheetapp.home.repository.api.factory.RetrofitFactory
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {

    single {
        GsonFactory.createGson()
    }

    single {
        OkHttpClientFactory.createOkHttpClient()
    }

    single {
        RetrofitFactory.createRetrofit(get(), get())
    }

    single<TestService> { get<Retrofit>().create(TestService::class.java) }

}
