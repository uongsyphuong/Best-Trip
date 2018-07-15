package vinova.intern.best_trip.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vinova.intern.best_trip.R
import vinova.intern.best_trip.model.Taxi
import vinova.intern.best_trip.taxiDetail.TaxiDetailActivity

class DataAdapter(var context: Context) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
         return DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taxi, parent, false))
     }

     override fun getItemCount(): Int {
         return taxiList.size
     }

     override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
         if (taxiList[position] != null) {
             val taxi = taxiList[position]!!
             Glide.with(context)
                     .load(taxi.logo)
                     .into(holder.image_taxi)
             holder.name_taxi.text = taxi.name
             holder.phone_taxi.text = taxi.phone
         }
         holder.itemView.setOnClickListener {
             val bundle = Bundle()
             bundle.putParcelable("taxi", taxiList[position])
             val intent = Intent (holder.itemView.context, TaxiDetailActivity::class.java ).putExtra("disTime", arrayOf("",""))
             intent.putExtras(bundle)
             holder.itemView.context.startActivity(intent)
         }
     }

     var taxiList: MutableList<Taxi?> = mutableListOf()

     inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


         var image_taxi: ImageView
          var phone_taxi: TextView
          var name_taxi: TextView

         init {
             image_taxi = itemView.findViewById(R.id.image_taxi)
             phone_taxi = itemView.findViewById(R.id.phone_taxi)
             name_taxi = itemView.findViewById(R.id.name_taxi)
         }
     }

     fun setData(data: MutableList<Taxi?>) {
         taxiList.clear()
         taxiList = data
         notifyDataSetChanged()
     }

    fun clearData() {
        taxiList.clear()
        notifyDataSetChanged()
    }
 }
