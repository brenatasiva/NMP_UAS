package id.web.rpgfantasy.protectcare51

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_card.view.*
import java.text.SimpleDateFormat
import java.util.*

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
            val formatter = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm")
            var date = Date((history.checkin).toLong() * 1000)
            var dateCheckin = formatter.format(date)
            textViewCheckIn.text = dateCheckin
            if (history.checkout == "0"){
                textViewCheckOut.text = "Not yet checked out"
            }
            else{
                date = Date((history.checkout).toLong() * 1000)
                val dateCheckout = formatter.format(date)
                textViewCheckOut.text = dateCheckout
            }
            if (history.vaccine == 2)
                cardViewHistory.setCardBackgroundColor(Color.parseColor("#8BC34A"))
            else if (history.vaccine == 1)
                cardViewHistory.setCardBackgroundColor(Color.parseColor("#FFEB3A"))
        }
    }

    override fun getItemCount(): Int = GlobalData.history.size
}