package czajkowski.patryk.testsheetapp.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

}
