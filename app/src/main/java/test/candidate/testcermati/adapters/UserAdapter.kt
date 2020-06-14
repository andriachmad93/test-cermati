package test.candidate.testcermati.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import test.candidate.testcermati.databinding.ItemUserBinding
import test.candidate.testcermati.models.UserModel

class UserAdapter : ListAdapter<UserModel, UserAdapter.ViewHolder>(ViewHolder.UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context)
                , parent
                , false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userItem = getItem(position)

        holder.apply {
            bind(
                userItem
                , position
            )
        }
    }

    class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var userModel: UserModel

        fun bind(
            userModel: UserModel
            , position: Int
        ) {
            this.userModel = userModel

            binding.apply {
                username = getUsernameGithub()
            }

            getUsernameImage()
        }

        private fun getUsernameImage() {
            Glide.with(binding.UserImage)
                .load(userModel.avatar_url)
                .into(binding.UserImage)
        }

        private fun getUsernameGithub() : String {
            if (!userModel.login.isNullOrBlank()) {
                return userModel.login.toString()
            } else {
                return ""
            }
        }

        class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
