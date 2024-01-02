package com.example.dapurbundabahagia.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.dapurbundabahagia.R
import com.example.dapurbundabahagia.app.di.RegisterViewModel
import com.example.dapurbundabahagia.app.utils.Status
import com.example.dapurbundabahagia.app.utils.Utils
import com.example.dapurbundabahagia.data.models.RegisterModel
import com.example.dapurbundabahagia.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModelRegister by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            btnRegister.setOnClickListener {
                // deklarasi variable
                val name = edtNama.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    viewModelRegister.register(RegisterModel(password, name, email))
                }else{
                    Snackbar.make(requireView(), "Please fill in the data completely", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            viewModelRegister.res.observe(viewLifecycleOwner){
                Utils.showLoading(progressIndicator, it.status == Status.LOADING)
                Utils.showLoading(overlay, it.status == Status.LOADING)
            }

            // signin
            mtvSignin.setOnClickListener {
                view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        val registerState = viewModelRegister.state
        lifecycleScope.launch {
            registerState.collect {
                if (it.isNavigate == true) {
                    // set fragment result when sucess to create new account
                    setFragmentResult(
                        Utils.GET_REQ_EMAIL,
                        bundleOf(Utils.GET_EMAIL_KEY to binding.edtEmail.text.toString())
                    )

                    setFragmentResult(
                        Utils.GET_REQ_PASSWORD,
                        bundleOf(Utils.GET_PASSWORD_KEY to binding.edtPassword.text.toString())
                    )

                    // give snackbar to msg
                    Snackbar.make(requireView(), "Account created successfully", Snackbar.LENGTH_SHORT)
                        .show()

                    // back to login fragment
                    findNavController().navigateUp()
                    viewModelRegister.navigates()
                }
                it.error?.also { error ->
                    Snackbar.make(
                        requireView(),
                        error,
                        Snackbar.LENGTH_SHORT
                    ).addCallback(
                        object : Snackbar.Callback() {
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                super.onDismissed(transientBottomBar, event)
                                viewModelRegister.showErrors()
                            }
                        }
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}