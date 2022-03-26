package com.example.gamergiveawaysapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamergiveawaysapp.R
import com.example.gamergiveawaysapp.adapter.GiveawayAdapter
import com.example.gamergiveawaysapp.databinding.FragmentPCGiveawaysBinding
import com.example.gamergiveawaysapp.databinding.FragmentPs4GiveawaysBinding
import com.example.gamergiveawaysapp.model.Giveaways
import com.example.gamergiveawaysapp.utils.GiveawayState
import com.example.gamergiveawaysapp.utils.PlatformType

class Ps4GiveawaysFragment : BaseFragment() {
    private val binding by lazy {
        FragmentPs4GiveawaysBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.ps4GiveawaysRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = giveawaysAdapter
        }
        giveawaysViewModel.giveaways.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GiveawayState.LOADING -> {
                    Toast.makeText(requireContext(), "loading...", Toast.LENGTH_LONG).show()
                }
                is GiveawayState.SUCCESS<*> -> {
                    val giveaways = state.giveaways as List<Giveaways>
                    giveawaysAdapter.setNewGiveaways(giveaways)
                }
                is GiveawayState.ERROR -> {
                    giveawaysAdapter.clearGiveaways()
                    Toast.makeText(
                        requireContext(),
                        state.error.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        giveawaysViewModel.getGiveawaysByPlatform()
        return binding.root
    }
}