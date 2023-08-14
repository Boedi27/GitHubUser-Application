package com.example.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.GithubUser
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.detail.DetailActivity

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var followerViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        followerViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        followerViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        followerViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
        }
        followerViewModel.getFollower(
            arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString()
        )
    }

    private fun setDataToFragment(listFollower: List<GithubUser>) {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.adapter = FollowAdapter(listFollower)
    }
}