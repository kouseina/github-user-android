package com.kouseina.githubuser.ui.searchUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouseina.githubuser.data.response.ItemsItem
import com.kouseina.githubuser.data.response.SearchUserResponse
import com.kouseina.githubuser.data.retrofit.ApiConfig
import com.kouseina.githubuser.databinding.FragmentSearchUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchUserFragment : Fragment() {
    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    private val searchUserViewModel by viewModels<SearchUserViewModel>()

    companion object {
        const val TAG = "SearchUserFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        searchUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        searchUserViewModel.userList.observe(viewLifecycleOwner) {
            setUserListData(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()

                    val searchText = searchView.text.toString()

                    val q = searchText.ifBlank { "daffa" }

                    searchUserViewModel.fetchSearchUser(q)

                    false
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUserListData(userList: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userList)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }
}