package id.whynot.github_user.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import id.whynot.github_user.model.person
import id.whynot.github_user.adapter.FolowerAdapter
import id.whynot.github_user.databinding.FragmentFollowerBinding
import id.whynot.github_user.viewmodel.FollowerViewModel



class FollowerFragment : Fragment() {


    private val listData: ArrayList<person> = ArrayList()
    private lateinit var adapter: FolowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    private lateinit var binding: FragmentFollowerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FolowerAdapter(listData)
        followerViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowerViewModel::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra<person>(EXTRA_PERSON)!!
        config()

        followerViewModel.getDataGit(
            requireActivity().applicationContext,
            dataUser.username.toString()
        )
        showLoading(true)

        followerViewModel.getListFollower().observe(requireActivity(), { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun config() {
        binding.recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewFollowers.setHasFixedSize(true)
        binding.recyclerViewFollowers.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbarFollowers.visibility = View.VISIBLE
        } else {
            binding.progressbarFollowers.visibility = View.INVISIBLE
        }
    }

    companion object {
        val TAG = FollowerFragment::class.java.simpleName
        const val EXTRA_PERSON = "extra_user"
    }
}
