package com.kenetic.savepass.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenetic.savepass.R
import com.kenetic.savepass.databinding.PassListBinding
import com.kenetic.savepass.password.PassEnum.Access
import com.kenetic.savepass.password.PasswordData
import com.kenetic.savepass.password.PasswordViewModel

private const val TAG = "PassAdapter"

class PassAdapter(
    private val viewModel: PasswordViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val fingerChecker: (PasswordData, Access) -> Unit
) :
    ListAdapter<Int, PassAdapter.PassViewHolder>(diffCallBack) {


    class PassViewHolder(
        private val binding: PassListBinding,
        private val fingerChecker: (PasswordData, Access) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(passwordData: PasswordData) {
            Log.i(TAG, "bind called")
            binding.isAppOrWebImageView.setImageResource(
                if (passwordData.isAnApplication) {
                    R.drawable.is_application_icon_24
                } else {
                    R.drawable.is_website_icon_24
                }
            )
            binding.serviceNameTextView.text = passwordData.serviceName

            if (passwordData.access) {
                Log.i(TAG, "access has been given")
                binding.apply {
                    servicePasswordTextView.text = passwordData.servicePassword
                    showImageView.setImageResource(R.drawable.ic_baseline_hide_20)
                    showImageView.setOnClickListener {
                        fingerChecker(passwordData, Access.HIDE)
                    }
                }
            } else {
                Log.i(TAG, "access has been denied")
                binding.showImageView.setImageResource(R.drawable.ic_baseline_show_20)
                binding.servicePasswordTextView.text = "**********"
                binding.showImageView.setOnClickListener {
                    fingerChecker(passwordData, Access.SHOW)
                }
            }

            binding.securityTypeImageView.setImageResource(
                if (passwordData.useFingerPrint) {
                    R.drawable.fingerprint_20
                } else {
                    R.drawable.password_20
                }
            )

            binding.deleteImageView.setOnClickListener {
                fingerChecker(passwordData, Access.DELETE)

            }
            binding.editImageView.setOnClickListener {
                Log.d(TAG, "edit image onClick working")
                fingerChecker(passwordData, Access.EDIT)
            }
        }
    }

    companion object {
        private val diffCallBack = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        return PassViewHolder(
            PassListBinding.inflate(LayoutInflater.from(parent.context)),
            fingerChecker
        )
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        viewModel.getById(getItem(position)).asLiveData().observe(lifecycleOwner) {
            try {
                holder.bind(it)
            } catch (e: Exception) {
                Log.e(TAG, "PassData passed = null")
                e.printStackTrace()
            }
        }
    }
}