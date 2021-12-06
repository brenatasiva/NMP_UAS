package id.web.rpgfantasy.protectcare51

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_card.view.*

class HistoryAdapter(): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var v = inflater.inflate(R.layout.history_card, parent, false)
        return HistoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = GlobalData.history[position]
        with(holder.view){
            textViewPlace.text = history.location
            textViewCheckIn.text = history.checkin
            textViewCheckOut.text = history.checkout
            if (history.vaccine == 2)
                cardView.setBackgroundColor(Color.parseColor("#00ff00"))
            else
                cardView.setCardBackgroundColor(Color.parseColor("ffff00"))
        }
    }

    override fun getItemCount(): Int = GlobalData.history.size
}