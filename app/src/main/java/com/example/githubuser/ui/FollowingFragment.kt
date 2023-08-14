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

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var followingViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        followingViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        followingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setDataToFragment(listFollowing)
        }
        followingViewModel.getFollowing(
            arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString()
        )
    }

    private fun setDataToFragment(listFollowing: List<GithubUser>) {
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.adapter = FollowAdapter(listFollowing)
    }
}