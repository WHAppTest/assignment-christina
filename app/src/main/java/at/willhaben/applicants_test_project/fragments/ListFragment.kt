package at.willhaben.applicants_test_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import at.willhaben.applicants_test_project.MainActivity
import at.willhaben.applicants_test_project.OpenDetailsScreenCallback
import at.willhaben.applicants_test_project.R
import at.willhaben.applicants_test_project.databinding.FragmentListBinding
import at.willhaben.applicants_test_project.listview.ListViewAdapter
import at.willhaben.applicants_test_project.usecasemodel.list.ListUseCaseModel
import at.willhaben.applicants_test_project.usecasemodel.list.ListUseCaseState
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class ListFragment : BaseFragment() {

    private val listUseCaseModel: ListUseCaseModel by inject()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var openDetailsScreenCallback: OpenDetailsScreenCallback
    private lateinit var listViewAdapter: ListViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { state -> listUseCaseModel.restoreState(state) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        openDetailsScreenCallback = (activity as MainActivity)
        listViewAdapter = ListViewAdapter(openDetailsScreenCallback)
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(activity, VERTICAL).apply {
                ContextCompat.getDrawable(context, R.drawable.line_divider)?.let { divider ->
                    setDrawable(divider)
                }
            })
            adapter = listViewAdapter
        }
        setAccordingToUiState(ListUseCaseState.Init)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listUseCaseModel.saveState(outState)
    }

    override fun onResume() {
        super.onResume()
        launch {
            for (newState in listUseCaseModel.getUIChannel()) {
                setAccordingToUiState(newState)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    private fun setAccordingToUiState(state: ListUseCaseState) {
        when (state) {
            is ListUseCaseState.Init -> listUseCaseModel.fetchData()
            is ListUseCaseState.Error -> {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
            }

            is ListUseCaseState.Loaded -> {
                binding.listLoadingView.visibility = View.GONE
                listViewAdapter.setItems(state.models.query?.search ?: emptyList())
            }

            ListUseCaseState.Loading -> {
                binding.listLoadingView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }
}