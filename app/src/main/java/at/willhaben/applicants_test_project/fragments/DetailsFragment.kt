package at.willhaben.applicants_test_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import at.willhaben.applicants_test_project.databinding.FragmentDetailBinding
import at.willhaben.applicants_test_project.usecasemodel.detail.DetailUseCaseModel
import at.willhaben.applicants_test_project.usecasemodel.detail.DetailUseCaseState
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class DetailsFragment : BaseFragment() {

    private val detailsUseCaseModel: DetailUseCaseModel by inject()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ARG_TITLE)?.let { title ->
            this.title = title
        }
        savedInstanceState?.getString(ARG_TITLE)?.let { title ->
            this.title = title
        }
        savedInstanceState?.let { state -> detailsUseCaseModel.restoreState(state) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailItemTitle.text = title
        binding.detailItemBanner.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                binding.detailItemBanner.loadUrl("chrome://crash")
                return true
            }

            override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
                activity?.supportFragmentManager?.popBackStack()
                return true
            }
        }
        setAccordingToUiState(DetailUseCaseState.Init)
    }

    override fun onResume() {
        super.onResume()
        launch {
            for (newState in detailsUseCaseModel.getUIChannel()) {
                setAccordingToUiState(newState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putString(ARG_TITLE, title)
        }
        detailsUseCaseModel.saveState(outState)
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    private fun setAccordingToUiState(state: DetailUseCaseState) {
        when (state) {
            is DetailUseCaseState.Init -> detailsUseCaseModel.fetchData(title ?: "")
            is DetailUseCaseState.Error -> {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_LONG).show()
            }

            is DetailUseCaseState.Loaded -> {
                binding.detailsLoadingView.visibility = View.GONE
                binding.detailItemBanner.loadUrl("file:///android_asset/banner.html")
                binding.detailItemBody.text = state.model.parse?.text
            }

            DetailUseCaseState.Loading -> {
                binding.detailsLoadingView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        private const val ARG_TITLE = "ARG_TITLE"

        fun newInstance(title: String): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
        }
    }
}