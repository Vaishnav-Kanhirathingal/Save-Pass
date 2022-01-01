package com.kenetic.savepass.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenetic.savepass.R
import com.kenetic.savepass.databinding.PassListBinding
import com.kenetic.savepass.password.PasswordData


class PassAdapter() :
    ListAdapter<PasswordData, PassAdapter.PassViewHolder>(diffCallBack) {

    class PassViewHolder(val binding: PassListBinding) : RecyclerView.ViewHolder(binding.root) {
        var verifiedWithPassword = false
        private val TAG = "PassAdapter"

        fun bind(passwordData: PasswordData) {
            binding.isAppOrWebImageView.setImageResource(
                if (passwordData.isAnApplication) {
                    R.drawable.is_application_icon_24
                } else {
                    R.drawable.is_website_icon_24
                }
            )
            binding.serviceNameTextView.text = passwordData.serviceName

            binding.servicePasswordTextView.text = passwordData.servicePassword

            binding.deleteImageView.setOnClickListener {
                Log.d(TAG, "delete image onClick working")
            }
            binding.showImageView.setOnClickListener {
                Log.d(TAG, "show image onClick working")
            }
            binding.editImageView.setOnClickListener {
                Log.d(TAG, "edit image onClick working")
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
        return PassViewHolder(PassListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PassViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}