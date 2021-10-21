package czajkowski.patryk.testsheetapp.home.repository

import android.util.Log
import czajkowski.patryk.testsheetapp.home.model.DummyDataLocal
import czajkowski.patryk.testsheetapp.home.model.DummyDataModelWrapper
import czajkowski.patryk.testsheetapp.home.repository.api.TestService
import czajkowski.patryk.testsheetapp.home.repository.db.DummyDataDao
import io.reactivex.*
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.math.pow

class DummyDataRepository(
    private val service: TestService,
    private val dummyDataDao: DummyDataDao
) {

    companion object {
    private const val RETRY_LIMIT = 3
    }

    fun getDummyData(id: Int = 1): Observable<DummyDataModelWrapper> {
        return Observable.concatArray(
            getDummyFromDb(id),
            getDummyFromApi(id)
        )
    }

    private fun getDummyFromDb(id: Int): Observable<DummyDataModelWrapper> {
        return dummyDataDao.selectDataById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorResumeNext(Maybe.empty())
            .toObservable()
            .map { DummyDataModelWrapper(it) }
            .doOnNext {
                Log.d("DummyDataRepository", "Dispatching ${it.id} from DB...")
            }
    }

    private fun getDummyFromApi(id: Int): Observable<DummyDataModelWrapper> {
        return service.getDummyData(id)
            .retryWhen { errors: Flowable<Throwable> ->
                errors.zipWith(
                    Flowable.range(1, RETRY_LIMIT + 1),
                    BiFunction<Throwable, Int, Int> { error: Throwable, retryCount: Int ->
                        if (error is TimeoutException && retryCount < RETRY_LIMIT) {
                            retryCount
                        } else {
                            throw error
                        }
                    }
                ).flatMap { retryCount ->
                    val delay = 2.toDouble().pow(retryCount.toDouble()).toLong() / 2
                    Flowable.timer(delay, TimeUnit.SECONDS)
                }}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorResumeNext { Single.error(it) }
            .toObservable()
            .map { DummyDataModelWrapper(it) }
            .doOnNext {
                Log.d("DummyDataRepository", "Dispatching ${it.id} from API...")
            }
    }

    fun updateDataOnDb(data: DummyDataModelWrapper): Completable {
        return dummyDataDao.deleteData(data.id)
            .andThen(Completable.defer {
                dummyDataDao.insertData(
                    DummyDataLocal(
                        id = data.id,
                        userId = data.userId,
                        title = data.title,
                        body = data.body
                    )
                )
            })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorResumeNext { Completable.error(it) }
            .doOnComplete {
                Log.d("DummyDataRepository", "Upadated Data ${data.id} in DB...")
            }
    }

}