package com.cubing.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cubing.githubuser.databinding.ItemRowUserBinding
import com.cubing.githubuser.model.User

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val listOfUser = ArrayList<User>()

    private var onUserSelectCallback : OnUserSelectCallback? = null

    inner class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.apply {
                Glide
                    .with(itemView)
                    .load(user.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgItemPhoto)
                tvItemName.text = user.name
                tvItemUsername.text = user.username
                tvItemRepository.text = user.repository
            }

            binding.root.setOnClickListener {
                onUserSelectCallback?.onUserSelected(user)
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listOfUser[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val listView = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(listView)
    }

    override fun getItemCount(): Int {
        return listOfUser.size
    }

    fun setListOfUser(listTempUser: ArrayList<User>){
        listOfUser.clear()
        listOfUser.addAll(listTempUser)
        notifyDataSetChanged()
    }

    interface OnUserSelectCallback{
        fun onUserSelected(dataUser:User)
    }

    fun setOnUserSelectCallback(onUserSelectCallback: OnUserSelectCallback) {
        this.onUserSelectCallback = onUserSelectCallback
    }
}