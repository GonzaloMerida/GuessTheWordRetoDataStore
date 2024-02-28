package com.example.guesstheword.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guesstheword.R
import com.example.guesstheword.databinding.GameItemBinding
import com.example.guesstheword.datamodel.Game

//Para actualizaciones constantes es mejor usar un ListAdapter.
class GameAdapter(
    private val gameList: List<Game>
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val binding = GameItemBinding.bind(view)

        fun bind(game: Game) {
            binding.tvItemDate.text = game.date
            binding.tvItemName.text = game.name
            binding.tvItemScore.text = game.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameViewHolder(layoutInflater.inflate(R.layout.game_item,parent,false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount(): Int {
        return gameList.size
    }
}