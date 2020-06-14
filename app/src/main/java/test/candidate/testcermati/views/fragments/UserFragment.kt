package test.candidate.testcermati.views.fragments

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.candidate.testcermati.adapters.UserAdapter
import test.candidate.testcermati.databinding.FragmentUserBinding
import test.candidate.testcermati.models.UserModel
import test.candidate.testcermati.models.UsersModel
import test.candidate.testcermati.utilities.InjectorUtils
import test.candidate.testcermati.viewmodels.UserViewModel
import java.util.ArrayList

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    var page = 1
    var searchUsername = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)

        searchUsers()

        binding.rcvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val visibleItemCount: Int = linearLayoutManager.getChildCount()
                val totalItemCount: Int = linearLayoutManager.getItemCount()
                val pastVisibleItems: Int = linearLayoutManager.findFirstCompletelyVisibleItemPosition()

                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    page++
                    getUsers(searchUsername, page)
                }
            }
        })

        return binding.root
    }

    private fun getUsers(username: String, page: Int) {
        binding.pgrLoad.visibility = View.VISIBLE

        Handler().postDelayed({
            val factory = InjectorUtils.provideUserViewModelFactory()

            userViewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)

            userViewModel.getUsers(username, page).observe(viewLifecycleOwner,
                object : Observer<UsersModel> {
                    override fun onChanged(users: UsersModel?) {
                        if (users != null && users.items!!.size > 0) {
                            adapter.submitList(users.items)
                            binding.userListSize = users.items?.size
                        } else {
                            Toast.makeText(context, "Mohon Maaf ada Kesalahan teknis", Toast.LENGTH_LONG).show()
                        }

                        binding.pgrLoad.visibility = View.GONE
                    }
                }
            )
        }, 5000)
    }

    private fun searchUsers() {
        binding.edtUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
                binding.userListSize = 0
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                adapter = UserAdapter()

                binding.rcvUserList.adapter = adapter
                binding.rcvUserList.layoutManager = linearLayoutManager

                if (charSequence?.length == 0) {
                    binding.userListSize = 0
                } else {
                    searchUsername = charSequence.toString()
                    getUsers(charSequence.toString(), page)
                }
            }

        })
    }
}
