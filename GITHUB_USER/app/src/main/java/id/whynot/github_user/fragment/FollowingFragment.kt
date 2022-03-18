package id.whynot.github_user.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import id.whynot.github_user.adapter.FollowingAdapter

import id.whynot.github_user.databinding.FragmentFollowingBinding
import id.whynot.github_user.model.person

import id.whynot.github_user.viewmodel.FollowingViewModel


class FollowingFragment : Fragment() {


    private val listData: ArrayList<person> = ArrayList()
    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingAdapter(listData)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val dataUser = requireActivity().intent.getParcelableExtra<person>(EXTRA_PERSON)!! as person
        config()

        followingViewModel.getDataGit(
            requireActivity().applicationContext,
            dataUser.username.toString()
        )
        showLoading(true)

        followingViewModel.getListFollowing().observe(requireActivity(), { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun config() {
        binding.recyclerViewFollowing.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewFollowing.setHasFixedSize(true)
        binding.recyclerViewFollowing.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressbarFollowing.visibility = View.INVISIBLE
        }
    }

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_PERSON = "extra_user"
    }
}
