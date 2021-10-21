package czajkowski.patryk.testsheetapp.util

import czajkowski.patryk.testsheetapp.home.view.DummyBottomDialogFragment

class ActionBottomSheet {
    companion object{
        const val TAG = "ActionBottomDialog"
        fun newInstance(): DummyBottomDialogFragment {
            return DummyBottomDialogFragment()
        }
    }
}