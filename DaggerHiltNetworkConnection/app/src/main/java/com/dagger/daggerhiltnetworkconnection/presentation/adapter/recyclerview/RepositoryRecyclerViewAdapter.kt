package com.dagger.daggerhiltnetworkconnection.presentation.adapter.recyclerview

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.dagger.daggerhiltnetworkconnection.Constants.INTENT_ARGUMENT_REPO_URL
import com.dagger.daggerhiltnetworkconnection.databinding.ItemUserRepositoryBinding
import com.dagger.daggerhiltnetworkconnection.presentation.extensions.openActivity
import com.dagger.daggerhiltnetworkconnection.presentation.ui.main.detail.web.RepoActivity
import com.orhanobut.logger.Logger
import kr.dagger.domain.model.UserRepo

class RepositoryRecyclerViewAdapter : RecyclerView.Adapter<RepositoryRecyclerViewAdapter.RepositoryViewHolder>() {

    private val items = mutableListOf<UserRepo>()

    fun setItems(items: List<UserRepo>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder = RepositoryViewHolder(
        ItemUserRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindRepo(repo = items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class RepositoryViewHolder constructor(private val binding: ItemUserRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
                val currentClickedAt = SystemClock.elapsedRealtime()

                binding.repoName.context.openActivity(RepoActivity::class.java) {
                    putString(INTENT_ARGUMENT_REPO_URL, items[position].html_url)
                }
//                Logger.d("positon :: $position\ncurrentClickedAt :: $currentClickedAt")
                Logger.d("position :: ${items[position].html_url}")
            }

        }

        fun bindRepo(repo : UserRepo) {
            binding.repo = repo

        }
    }


}