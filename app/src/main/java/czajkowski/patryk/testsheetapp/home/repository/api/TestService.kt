package czajkowski.patryk.testsheetapp.home.repository.api

import czajkowski.patryk.testsheetapp.home.model.DummyDataResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TestService {

    @GET("posts/{id}")
    fun getDummyData(@Path ("id") id: Int): Single<DummyDataResponse>

}