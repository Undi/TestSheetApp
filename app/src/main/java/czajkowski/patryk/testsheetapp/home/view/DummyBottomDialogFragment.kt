package czajkowski.patryk.testsheetapp.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import czajkowski.patryk.testsheetapp.R
import czajkowski.patryk.testsheetapp.databinding.BottomSheetBinding
import czajkowski.patryk.testsheetapp.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DummyBottomDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = BottomSheetBinding.bind(view)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            (context?.getString(R.string.bottom_sheet_id_label) + it.id.toString()).also { binding.id.text = it }
            (context?.getString(R.string.bottom_sheet_userid_label) + it.userId.toString()).also { binding.userId.text = it }
            (context?.getString(R.string.bottom_sheet_title_label) + it.title).also { binding.title.text = it }
            (context?.getString(R.string.bottom_sheet_body_label) + it.body).also { binding.body.text = it }
        })

        binding.buttonClose.setOnClickListener {
            viewModel.isBottomSheetExpanded.postValue(false)
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}