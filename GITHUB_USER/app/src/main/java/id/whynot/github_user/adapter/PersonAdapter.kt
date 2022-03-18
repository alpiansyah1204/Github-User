package id.whynot.github_user.adapter


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.whynot.github_user.DetailActivity
import id.whynot.github_user.databinding.ItemUserBinding
import id.whynot.github_user.model.person
import java.util.ArrayList

class PersonAdapter (private val listDataUsersGithub: ArrayList<person>) :
    RecyclerView.Adapter<PersonAdapter.ListDataHolder>() {

    fun setData(items: ArrayList<person>) {
        Log.d("personadapter", "items size:${items.size} ")
        listDataUsersGithub.clear()
        listDataUsersGithub.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataUsers: person) {
            Glide.with(itemView.context)
                .load(dataUsers.avatar)
                .circleCrop()
                .into(binding.ivItemAvatar)

            binding.tvItemName.text = dataUsers.name
            binding.tvItemUsername.text = dataUsers.username
            binding.tvItemCompany.text = dataUsers.company
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDataHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListDataHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDataUsersGithub.size
    }

    override fun onBindViewHolder(holder: ListDataHolder, position: Int) {
        holder.bind(listDataUsersGithub[position])

        val data = listDataUsersGithub[position]
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