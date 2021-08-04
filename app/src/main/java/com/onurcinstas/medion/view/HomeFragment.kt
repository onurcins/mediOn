package com.onurcinstas.medion.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onurcinstas.medion.R
import com.onurcinstas.medion.adapter.HomeAdapter
import com.onurcinstas.medion.databinding.FragmentHomeBinding
import com.onurcinstas.medion.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = resources.getColor(R.color.black, resources.newTheme())

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getDataFromAPI()

        adapter = HomeAdapter(requireContext(), arrayListOf())

        binding.apply {
            homeRecyclerView.adapter = adapter
            homeRecyclerView.layoutManager = LinearLayoutManager(context)
        }


        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.item.observe(viewLifecycleOwner, { item ->

            item?.let {
                if (context != null) {
                    val arr = viewModel.getArr(requireContext(), it)
                    adapter.updateList(arr)
                }

            }

        })

        viewModel.error.observe(viewLifecycleOwner,  { error->
            error?.let {
                if(it) {
                    binding.homeErr.visibility = View.VISIBLE
                } else {
                    binding.homeErr.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { loading->
            loading?.let {
                if (it) {
                    binding.homeProgressbar.visibility = View.VISIBLE
                    binding.homeRecyclerView.visibility = View.GONE
                    binding.homeErr.visibility = View.GONE
                } else {
                    binding.homeProgressbar.visibility = View.GONE
                    binding.homeRecyclerView.visibility = View.VISIBLE
                }
            }
        })
    }
}