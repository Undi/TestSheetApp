package czajkowski.patryk.testsheetapp.home.viewmodel

import androidx.lifecycle.MutableLiveData
import czajkowski.patryk.testsheetapp.base.BaseViewModel
import czajkowski.patryk.testsheetapp.home.model.DummyDataModelWrapper
import czajkowski.patryk.testsheetapp.home.repository.DummyDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val dummyDataRepository: DummyDataRepository
) : BaseViewModel() {

    val errorMessage = MutableLiveData<String>()
    val data = MutableLiveData<DummyDataModelWrapper>()
    val isProgressBarVisible = MutableLiveData<Boolean>()
    val isBottomSheetExpanded = MutableLiveData<Boolean>()

    fun fetchPlace(id: Int) {
        isProgressBarVisible.postValue(true)
        isBottomSheetExpanded.postValue(false)
        dummyDataRepository.getDummyData(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                isProgressBarVisible.postValue(false)
                isBottomSheetExpanded.postValue(true)
            }
            .subscribeBy(
                onError = {
                    errorMessage.postValue(it.message)
                }, onNext = {
                    dummyDataRepository.updateDataOnDb(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeBy(
                            onError = {
                                errorMessage.postValue(it.message)
                            },
                            onComplete = {
                                data.postValue(it)
                            }
                        ).addTo(disposables)
                }
            ).addTo(disposables)
    }

}