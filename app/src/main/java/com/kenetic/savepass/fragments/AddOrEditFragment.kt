package com.kenetic.savepass.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kenetic.savepass.databinding.FragmentAddOrEditBinding
import com.kenetic.savepass.password.PasswordApplication
import com.kenetic.savepass.password.PasswordData
import com.kenetic.savepass.password.PasswordViewModel
import com.kenetic.savepass.password.PasswordViewModelFactory

class AddOrEditFragment : Fragment() {
    private val viewModel: PasswordViewModel by activityViewModels {
        PasswordViewModelFactory(
            (activity?.application as PasswordApplication).database.passwordDao()
        )
    }
    private lateinit var binding: FragmentAddOrEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddOrEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            this.findNavController()
                .navigate(AddOrEditFragmentDirections.actionAddOrEditFragmentToPassListFragment())
        }
        binding.saveButton.setOnClickListener {
            //todo - save to database
            binding.serviceNameEmptyTextView.isVisible =
                binding.serviceNameEditText.text.isNullOrEmpty()
            binding.servicePasswordEmptyTextView.isVisible =
                binding.servicePasswordEditText.text.isNullOrEmpty()

            if (!(binding.servicePasswordEditText.text.isNullOrEmpty() || binding.serviceNameEditText.text.isNullOrEmpty())) {
                viewModel.insert(
                    PasswordData(
                        serviceName = binding.serviceNameEditText.text.toString(),
                        servicePassword = binding.servicePasswordEditText.text.toString(),
                        useFingerPrint = binding.fingerprintCheckBox.isChecked,
                        isAnApplication = binding.applicationServiceRadioButton.isChecked,
                        access = false
                    )
                )
                this.findNavController()
                    .navigate(AddOrEditFragmentDirections.actionAddOrEditFragmentToPassListFragment())
            }
        }
    }
}