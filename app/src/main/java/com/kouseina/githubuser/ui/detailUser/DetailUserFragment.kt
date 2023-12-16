package com.kouseina.githubuser.ui.detailUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.kouseina.githubuser.R
import com.kouseina.githubuser.data.response.DetailUserResponse
import com.kouseina.githubuser.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailUserViewModel>()

    companion object {
        val TAG = "DetailUserFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = DetailUserFragmentArgs.fromBundle(arguments as Bundle).username

        viewModel.fetchDetailUser(username)

        viewModel.detailUser.observe(viewLifecycleOwner){
            setDetailUserData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    fun setDetailUserData(detailUserData: DetailUserResponse) {
        Glide.with(requireActivity())
            .load(detailUserData.avatarUrl)
            .into(binding.imageView)
        binding.tvUsername.text = detailUserData.login
        binding.tvName.text = detailUserData.name
        binding.tvFollowers.text = this@DetailUserFragment.resources.getString(R.string.followers, detailUserData.followers ?: 0)
        binding.tvFollowing.text = this@DetailUserFragment.resources.getString(R.string.following, detailUserData.following ?: 0)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }
}