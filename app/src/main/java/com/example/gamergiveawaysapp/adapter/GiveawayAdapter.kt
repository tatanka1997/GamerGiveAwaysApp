package com.example.gamergiveawaysapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamergiveawaysapp.databinding.GiveawayItemBinding
import com.example.gamergiveawaysapp.model.Giveaways
import com.squareup.picasso.Picasso

class GiveawayAdapter(
    private val giveaways: MutableList<Giveaways> = mutableListOf()
) : RecyclerView.Adapter<GiveAwayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveAwayViewHolder {
        return GiveAwayViewHolder(
            GiveawayItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GiveAwayViewHolder, position: Int) =
        holder.bind(giveaways[position])

    override fun getItemCount(): Int = giveaways.size

    fun setNewGiveaways(newGiveaways: List<Giveaways>) {
        giveaways.clear()
        giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }
    fun clearGiveaways(){
        giveaways.clear()
        notifyDataSetChanged()
    }
}


class GiveAwayViewHolder(
    private val binding: GiveawayItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(giveaways: Giveaways) {
        binding.giveawayDate.text = giveaways.endDate
        binding.giveawayPlatform.text = giveaways.platforms
        binding.giveawayTitle.text = giveaways.title
        binding.giveawayWorth.text = giveaways.worth
        binding.giveawayStatus.text = giveaways.status

        Picasso.get()
            .load(giveaways.image)
            .into(binding.giveawayImage)
    }

}