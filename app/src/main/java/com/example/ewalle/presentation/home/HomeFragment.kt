package com.example.ewalle.presentation.home

import com.example.ewalle.presentation.base.BaseFragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ewalle.App
import com.example.ewalle.R
import com.example.ewalle.data.net.NetReqState
import com.example.ewalle.databinding.FragmentHomeBinding
import java.text.NumberFormat
import java.util.*

class HomeFragment : BaseFragment() {

    companion object {
        private const val SIZE_COLUMN = 4
    }

    private lateinit var viewModel: HomeViewModel

    private var usersAdapter: UsersAdapter? = null
    private var servicesAdapter: ServicesAdapter? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun initDagger() {
        (requireActivity().application as App).getAppComponent()
            .registerHomeComponent()
            .create()
            .inject(this)
    }

    override fun initViewModule() {
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
        initRecyclerUsers()
        initRecyclerServices()
        initData()
    }

    private fun initData() {
        viewModel.getCurrentData()
    }

    private fun setObservers() {
        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.homeBalance.text = NumberFormat.getNumberInstance(Locale.US).format(balance)
        }
        viewModel.listUsers.observe(viewLifecycleOwner) { listUsers ->
            usersAdapter?.setListUsers(listUsers)
        }
        viewModel.listServices.observe(viewLifecycleOwner) { listService ->
            servicesAdapter?.setListUsers(listService)
        }
        viewModel.networkState.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    NetReqState.SUCCESS -> {
                        binding.shimmerBalance.isVisible = false
                        binding.shimmerServices.isVisible = false
                        binding.shimmerUsers.isVisible = false
                        binding.homeBalance.isVisible = true
                        binding.homeRecyclerSevice.isVisible = true
                        binding.homeRecyclerUsers.isVisible = true
                    }
                    NetReqState.ERROR -> {
                        AlertDialog.Builder(context).setTitle(getString(R.string.error_title))
                            .setMessage(getString(R.string.error_mesege))
                            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                                viewModel.getCurrentData()
                            }
                            .setNegativeButton(getString(R.string.no)) { _, _ ->
                            }.create().show()
                    }
                }
            }
        }
    }


    private fun initRecyclerUsers() {
        usersAdapter = UsersAdapter(onClickButton =
        {
            Toast.makeText(context, getString(R.string.toast_add_users), Toast.LENGTH_SHORT).show()
        },
            onClickUsers = { position ->
                Toast.makeText(context, "Users $position", Toast.LENGTH_SHORT).show()
            })
        binding.homeRecyclerUsers.apply {
            addItemDecoration(UsersItemDecoration())
            adapter = usersAdapter
        }
    }

    private fun initRecyclerServices() {
        servicesAdapter = ServicesAdapter(onClick = { position ->
            Toast.makeText(context, "Services $position", Toast.LENGTH_SHORT).show()
        })
        binding.homeRecyclerSevice.apply {
            layoutManager = GridLayoutManager(context, SIZE_COLUMN)
            addItemDecoration(ServicesItemDecoration())
            adapter = servicesAdapter
        }
    }

    private fun setListeners() {
        binding.homeAddBalance.setOnClickListener {
            Toast.makeText(context, getString(R.string.roast_add), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        usersAdapter = null
        servicesAdapter = null
    }
}