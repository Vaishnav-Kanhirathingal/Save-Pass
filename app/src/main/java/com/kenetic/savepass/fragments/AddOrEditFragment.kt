package com.kenetic.savepass.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.FingerprintDialogFragment
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kenetic.savepass.databinding.FragmentAddOrEditBinding
import com.kenetic.savepass.password.PasswordApplication
import com.kenetic.savepass.password.PasswordData
import com.kenetic.savepass.password.PasswordViewModel
import com.kenetic.savepass.password.PasswordViewModelFactory

class AddOrEditFragment : Fragment() {
    private val TAG = "AddOrEditFragment"
    private val viewModel: PasswordViewModel by activityViewModels {
        PasswordViewModelFactory(
            (activity?.application as PasswordApplication).database.passwordDao()
        )
    }

    private lateinit var binding: FragmentAddOrEditBinding
    private lateinit var argServiceName: String
    private lateinit var argServicePassword: String
    private var argId: Int = 0
    private var argIsAnApplication: Boolean = false
    private var argUseFingerprint: Boolean = true
    private var argIsBeingUpdated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments.let {
            Log.d(TAG, "arguments.let started")
            argServiceName = it!!.getString("service_name")!!
            argServicePassword = it.getString("service_password")!!
            argIsAnApplication = it.getBoolean("is_an_application")
            argUseFingerprint = it.getBoolean("use_finger_print")
            argIsBeingUpdated = it.getBoolean("is_being_updated")
            argId = it.getInt("id")
            Log.d(
                TAG,
                "argServiceName = $argServiceName , pass = $argServicePassword , app = $argIsAnApplication , finger = $argUseFingerprint , update = $argIsBeingUpdated"
            )
        }
        binding = FragmentAddOrEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            this.findNavController()
                .navigate(AddOrEditFragmentDirections.actionAddOrEditFragmentToPassListFragment())
        }

        if (argIsBeingUpdated) {
            binding.apply {
                serviceNameEditText.editText!!.setText(argServiceName)
                servicePasswordEditText.editText!!.setText(argServicePassword)
                fingerprintCheckBox.isChecked = argUseFingerprint
            }
            if (argIsAnApplication) {
                binding.applicationServiceRadioButton.isChecked = true
            } else {
                binding.internetServiceRadioButton.isChecked = true
            }
        } else {
            binding.internetServiceRadioButton.isChecked = true
            binding.fingerprintCheckBox.isChecked = true
        }

        binding.saveButton.setOnClickListener {
            if (!(binding.servicePasswordEditText.editText!!.text.isNullOrEmpty() || binding.serviceNameEditText.editText!!.text.isNullOrEmpty())) {

                if (argIsBeingUpdated) {
                    viewModel.update(
                        PasswordData(
                            id = argId,
                            serviceName = binding.serviceNameEditText.editText!!.text.toString(),
                            servicePassword = binding.servicePasswordEditText.editText!!.text.toString(),
                            useFingerPrint = binding.fingerprintCheckBox.isChecked,
                            isAnApplication = binding.applicationServiceRadioButton.isChecked,
                            access = false
                        )
                    )
                } else {
                    viewModel.insert(
                        PasswordData(
                            serviceName = binding.serviceNameEditText.editText!!.text.toString(),
                            servicePassword = binding.servicePasswordEditText.editText!!.text.toString(),
                            useFingerPrint = binding.fingerprintCheckBox.isChecked,
                            isAnApplication = binding.applicationServiceRadioButton.isChecked,
                            access = false
                        )
                    )
                }
                this.findNavController()
                    .navigate(AddOrEditFragmentDirections.actionAddOrEditFragmentToPassListFragment())
            } else {
                binding.serviceNameEditText.error =
                    if (binding.serviceNameEditText.editText!!.text.isNullOrEmpty()) {
                        "*field empty"
                    } else {
                        ""
                    }
                binding.servicePasswordEditText.error =
                    if (binding.servicePasswordEditText.editText!!.text.isNullOrEmpty()) {
                        "*field empty"
                    } else {
                        ""
                    }
            }
        }
    }
}