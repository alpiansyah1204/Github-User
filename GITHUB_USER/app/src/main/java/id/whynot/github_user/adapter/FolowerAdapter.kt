package id.whynot.github_user.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.whynot.github_user.DetailActivity
import id.whynot.github_user.databinding.ItemUserBinding
import id.whynot.github_user.model.person

class FolowerAdapter(private val listFollower: ArrayList<person>) :
    RecyclerView.Adapter<FolowerAdapter.ListDataHolder>() {
    fun setData(items: ArrayList<person>) {
        listFollower.clear()
        listFollower.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataFollowers: person) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .circleCrop()
                    .into(binding.ivItemAvatar)

                binding.tvItemName.text = dataFollowers.name
                binding.tvItemUsername.text = dataFollowers.username
                binding.tvItemCompany.text = dataFollowers.company


                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDataHolder(binding)

    }

    override fun getItemCount(): Int {
        return listFollower.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listFollower[position])


        val data = listFollower[position]
        holder.itemView.setOnClickListener {
            val dataUserIntent = person(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.repository,
                data.followers,
                data.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_PERSON, dataUserIntent)
            it.context.startActivity(mIntent)
        }


    }
}