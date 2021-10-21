package czajkowski.patryk.testsheetapp.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import czajkowski.patryk.testsheetapp.R
import czajkowski.patryk.testsheetapp.databinding.FragmentHomeBinding
import czajkowski.patryk.testsheetapp.home.viewmodel.HomeViewModel
import czajkowski.patryk.testsheetapp.util.ActionBottomSheet
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding? = null
    private val bottomSheetDialogFragment: DummyBottomDialogFragment =
        ActionBottomSheet.newInstance()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.buttonPlay.setOnClickListener {
            viewModel.fetchPlace(1)
        }

        viewModel.isProgressBarVisible.observe(viewLifecycleOwner, Observer { toShow ->
            if (toShow) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.isBottomSheetExpanded.observe(viewLifecycleOwner, Observer { toExpand ->
            if (toExpand) {
                expandBottomSheet()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun expandBottomSheet() {
        bottomSheetDialogFragment.isCancelable = false
        activity?.supportFragmentManager?.let {
            if (it.findFragmentByTag(ActionBottomSheet.TAG) == null) {
                bottomSheetDialogFragment.show(it, ActionBottomSheet.TAG)
            }
        }
    }

}