package br.com.dhungria.combinedatemovies.ui.support

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.dhungria.combinedatemovies.databinding.SupportFragmentBinding
import br.com.dhungria.combinedatemovies.viewmodel.SupportViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupportFragment: Fragment() {

    private lateinit var binding: SupportFragmentBinding
    private lateinit var mAdView: AdView
    private val viewModel: SupportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SupportFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBannerAd()
//        binding.buttonSendSupportFragment.text = "Enviar"
        binding.buttonSendSupportFragment.setOnClickListener {
            val title = binding.textInputEditTitleSupportFragment.text.toString()
            val text = binding.textInputEditTextSupportFragment.text.toString()
            viewModel.uploadSupport(
                title = title,
                text = text,
                context = context
            ) { success ->
                if (success) {
                    binding.buttonSendSupportFragment.text = "Enviado!"
                    binding.textInputEditTitleSupportFragment.text?.clear()
                    binding.textInputEditTextSupportFragment.text?.clear()
                    val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
                } else {
                    binding.buttonSendSupportFragment.text = "Serviço Indisponível"
                }
            }
        }
    }

    private fun setupBannerAd() {
        binding.adViewBannerSupportFragment.visibility = View.VISIBLE
        mAdView = binding.adViewBannerSupportFragment
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }


}