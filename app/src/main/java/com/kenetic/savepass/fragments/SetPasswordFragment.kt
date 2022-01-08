package com.kenetic.savepass.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.kenetic.savepass.databinding.FragmentSetPasswordBinding
import com.kenetic.savepass.password.data.AppDataStore

class SetPasswordFragment : Fragment() {
    private val TAG = "SetPasswordFragmentVKP"

    private lateinit var binding: FragmentSetPasswordBinding

    private lateinit var appDataStore: AppDataStore
    private lateinit var storedPassword: String

    private var _symbolsVisibility = MutableLiveData(View.INVISIBLE)
    val symbolsVisibility: MutableLiveData<Int> get() = _symbolsVisibility

    private var _passwordMatchVisibility = MutableLiveData(View.INVISIBLE)
    val passwordMatchVisibility: MutableLiveData<Int> get() = _passwordMatchVisibility

    private var _passwordIncorrectVisibility = MutableLiveData(View.INVISIBLE)
    val passwordIncorrectVisibility: MutableLiveData<Int> get() = _passwordIncorrectVisibility

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetPasswordBinding.inflate(inflater, container, false)
        binding.setPasswordFragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDataStore = AppDataStore(requireContext())
        appDataStore.userMasterPasswordFlow.asLiveData().observe(viewLifecycleOwner, {
            storedPassword = it
            if (it.isEmpty()) {
                binding.oldPasswordEditText.visibility = View.GONE
                _passwordIncorrectVisibility.value = View.GONE
            }
        })
        binding.saveFab.setOnClickListener { passToNextFrag() }

    }

    private lateinit var symbolMissingText: String

    private fun getSymbolCheckWarning(temp: String): String {
        symbolMissingText = ""
        val tempTruthList = listOf<Boolean>(
            hasUpperCase(temp),
            hasLowerCase(temp),
            hasSpecial(temp),
            hasNumbers(temp),
            temp.isNotEmpty()
        )
        return if (false !in tempTruthList) {
            ""
        } else {
            "*missing character types -${symbolMissingText} "
        }
    }

    private fun hasLowerCase(str: String): Boolean {
        for (i in str) {
            if (i in 'a'..'z') {
                return true
            }
        }
        symbolMissingText += " lowercase,"
        return false
    }

    private fun hasUpperCase(str: String): Boolean {
        for (i in str) {
            if (i in 'A'..'Z') {
                return true
            }
        }
        symbolMissingText += " uppercase,"
        return false
    }

    private fun hasSpecial(str: String): Boolean {
        for (i in str) {
            if (i in "`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/") {
                return true
            }
        }
        symbolMissingText += " special,"
        return false
    }

    private fun hasNumbers(str: String): Boolean {
        for (i in str) {
            if (i in '0'..'9') {
                return true
            }
        }
        symbolMissingText += " numbers,"
        return false
    }

    private fun getLengthWarningString(str: String): String {
        return if (str.length in 9..31) "" else {
            "*length should be between 8-32 characters"
        }
    }

    private fun setNewPassWarning(): Boolean {
        val str = binding.setNewPasswordEditText.text.toString()
        val warningText = getSymbolCheckWarning(str) + getLengthWarningString(str)
        Log.i(TAG, "warningText = $warningText")
        return if (warningText.isEmpty()) {
            _symbolsVisibility.value = View.INVISIBLE
            true
        } else {
            binding.setNewTextView.text = warningText
            _symbolsVisibility.value = View.VISIBLE
            false
        }
    }

    private fun matchCheck(): Boolean {
        return if (binding.setNewPasswordEditText.text.toString() == binding.confirmNewPasswordEditText.text.toString()) {
            _passwordMatchVisibility.value = View.INVISIBLE
            true
        } else {
            _passwordMatchVisibility.value = View.VISIBLE
            false
        }
    }

    private fun incorrectCheck(): Boolean {
        return if (binding.oldPasswordEditText.text.toString() != storedPassword) {
            _passwordIncorrectVisibility.value = View.VISIBLE
            false
        } else {
            _passwordIncorrectVisibility.value = View.INVISIBLE
            true
        }
    }

    private fun passToNextFrag() {
        val tempTruthList = listOf(setNewPassWarning(), matchCheck(), incorrectCheck())
        if (false !in tempTruthList) {
            appDataStore.editMasterPassword(
                binding.setNewPasswordEditText.text.toString(),
                requireContext()
            )
            nextScreen()

        } else {
            Toast.makeText(requireContext(), "retry again", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextScreen() {
        Log.i(TAG,"next screen called")
        this@SetPasswordFragment.findNavController()
            .navigate(
                SetPasswordFragmentDirections
                    .actionSetPasswordFragmentToPassListFragment()
            )
    }
}