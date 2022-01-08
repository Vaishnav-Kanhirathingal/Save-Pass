package com.kenetic.savepass.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenetic.savepass.R
import com.kenetic.savepass.databinding.PassListBinding
import com.kenetic.savepass.password.PassEnum.Access
import com.kenetic.savepass.password.PasswordData

class PassAdapter(private val fingerChecker: (PasswordData, Access) -> Unit) :
    ListAdapter<PasswordData, PassAdapter.PassViewHolder>(diffCallBack) {

    private val TAG = "PassAdapter"

    class PassViewHolder(
        private val binding: PassListBinding,
        private val fingerChecker: (PasswordData, Access) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val TAG = "PassAdapter"
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
                binding.servicePasswordTextView.text = passwordData.servicePassword
                binding.showImageView.setImageResource(R.drawable.ic_baseline_hide_20)
                binding.showImageView.setOnClickListener {
                    fingerChecker(passwordData, Access.HIDE)
                }
            } else {
                Log.i(TAG, "access has been denied")
                binding.showImageView.setImageResource(R.drawable.ic_baseline_show_20)
                binding.servicePasswordTextView.text = "**********"
                binding.showImageView.setOnClickListener {
                    fingerChecker(passwordData, Access.SHOW)
                }
            }

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
        private val diffCallBack = object : DiffUtil.ItemCallback<PasswordData>() {
            override fun areItemsTheSame(oldItem: PasswordData, newItem: PasswordData) =
                (oldItem.id == newItem.id)

            override fun areContentsTheSame(oldItem: PasswordData, newItem: PasswordData) =
                (oldItem == newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassViewHolder {
        return PassViewHolder(
            PassListBinding.inflate(LayoutInflater.from(parent.context)), fingerChecker
        )
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        Log.i(TAG, "access for $position = ${getItem(position).access}")
        holder.bind(getItem(position))
    }
}