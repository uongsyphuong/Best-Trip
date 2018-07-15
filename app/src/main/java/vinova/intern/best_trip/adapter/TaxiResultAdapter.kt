package vinova.intern.best_trip.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vinova.intern.best_trip.R
import vinova.intern.best_trip.model.Taxi
import vinova.intern.best_trip.taxiDetail.TaxiDetailActivity
import java.text.DecimalFormat

class TaxiResultAdapter: RecyclerView.Adapter<TaxiResultAdapter.TaxiResultHolder>() {
	var taxiList: MutableList<Taxi?> = mutableListOf()
	var ori_desti : String = ""
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiResultHolder {
		return TaxiResultHolder(LayoutInflater.from(parent.context).inflate(R.layout.taxi_result_item, parent, false))
	}

	override fun getItemCount(): Int {
		return taxiList.size
	}

	override fun onBindViewHolder(holder: TaxiResultHolder, position: Int) {
		val formatter = DecimalFormat("#,###")
		if (taxiList[position] != null) {
			val taxi = taxiList[position]!!
			holder.name_taxi.text = taxi.name
			holder.phone_taxi.text = taxi.phone
			if (taxi.fourSeaters != null) {
				holder.price.text = formatter.format( taxi.priceFour)
			}
			else holder.price.text = formatter.format( taxi.priceSeven)

		}

		holder.itemView.setOnClickListener {
			val bundle = Bundle()
			bundle.putParcelable("taxi", taxiList[position])
			val intent = Intent (holder.itemView.context, TaxiDetailActivity::class.java )
			intent.putExtras(bundle).putExtra("desti",ori_desti)
			holder.itemView.context.startActivity(intent)
		}
	}



	inner class TaxiResultHolder(itemView: View):RecyclerView.ViewHolder(itemView){
		var name_taxi: TextView
		var phone_taxi: TextView
		var price :TextView

		init {
		    name_taxi = itemView.findViewById(R.id.name_taxi)
			phone_taxi = itemView.findViewById(R.id.phone_taxi)
			price = itemView.findViewById(R.id.price)
		}
	}
	fun setData(data:MutableList<Taxi?>,address:String){
		taxiList.clear()
		taxiList = data
		ori_desti = address
		notifyDataSetChanged()
	}
}