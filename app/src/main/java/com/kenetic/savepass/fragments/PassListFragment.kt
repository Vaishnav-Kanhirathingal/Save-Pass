package com.kenetic.savepass.fragments

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenetic.savepass.adapters.PassAdapter
import com.kenetic.savepass.databinding.FragmentPassListBinding
import com.kenetic.savepass.password.PasswordApplication
import com.kenetic.savepass.password.PasswordData
import com.kenetic.savepass.password.PasswordViewModel
import com.kenetic.savepass.password.PasswordViewModelFactory
import com.kenetic.savepass.password.PassEnum.Access
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PassListFragment : Fragment() {
    private var access: Access? = null
    private var passData: PasswordData? = null
    private val TAG = "PassListFragment"
    private val viewModel: PasswordViewModel by activityViewModels {
        PasswordViewModelFactory(
            (activity?.application as PasswordApplication).database.passwordDao()
        )
    }
    private lateinit var binding: FragmentPassListBinding

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPassListBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.passwordRecyclerView
        recyclerView.layoutManager = GridLayoutManager(this.requireContext(), 1)
        //todo - adapter adapter adapter adapter adapter adapter adapter adapter adapter adapter adapter adapter
        val adapter = PassAdapter{ pass: PasswordData, acc: Access ->
            if (acc != Access.HIDE) {
                access = acc
                passData = pass
                verifyFingerPrint()
            } else {
                viewModel.resetPreviousAccess()
            }
        }
        recyclerView.adapter = adapter
        viewModel.passList.observe(this.viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.addFab.setOnClickListener {
            val action =
                PassListFragmentDirections.actionPassListFragmentToAddOrEditFragment(isBeingUpdated = false, id = 0)
            this.findNavController().navigate(action)
        }
        viewModel.resetAllAccess()//todo - check validity
    }

    override fun onPause() {
        viewModel.resetPreviousAccess()
        super.onPause()
    }

    //todo - fingerprint functions fingerprint functions fingerprint functions fingerprint functions functions functions functions
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d(TAG, "authentication error")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    Log.d(TAG, "authentication succeeded")
                    when (access) {
                        Access.SHOW -> viewModel.getAccess(passData!!)
                        Access.EDIT -> {
                            this@PassListFragment
                                .findNavController()
                                .navigate(
                                    PassListFragmentDirections.actionPassListFragmentToAddOrEditFragment(
                                        serviceName = passData!!.serviceName,
                                        servicePassword = passData!!.servicePassword,
                                        isAnApplication = passData!!.isAnApplication,
                                        useFingerPrint = passData!!.useFingerPrint,
                                        isBeingUpdated = true,
                                        id = passData!!.id
                                    )
                                )
                            //todo - navigate navigate navigate navigate navigate navigate navigate navigate navigate navigate navigate
                        }
                        Access.DELETE -> viewModel.delete(passData!!)
                        else -> Toast.makeText(
                            this@PassListFragment.context,
                            "Fatal error has occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Log.d(TAG, "authentication failed")
        }
        return cancellationSignal as CancellationSignal
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkBiometricSupport(): Boolean {
        val keyGuardManager: KeyguardManager =
            activity?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return (
                (keyGuardManager.isKeyguardSecure)
                        &&
                        (ActivityCompat.checkSelfPermission(
                            this.requireContext(),
                            android.Manifest.permission.USE_BIOMETRIC
                        ) == PackageManager.PERMISSION_GRANTED))

                &&
                requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun verifyFingerPrint() {
        if (checkBiometricSupport()) {
            BiometricPrompt.Builder(this.requireContext())
                .setTitle("verify finger for password to ${passData?.serviceName}")
                .setSubtitle("Authentication required")
                .setDescription("")
                .setNegativeButton(
                    "cancel",
                    this.requireActivity().mainExecutor,
                    { _, _ -> })
                .build()
                .authenticate(
                    getCancellationSignal(),
                    requireContext().mainExecutor,
                    authenticationCallback
                )
        } else {
            Toast.makeText(requireContext(), "Fingerprint Support Not Found", Toast.LENGTH_SHORT)
                .show()
        }
    }
}