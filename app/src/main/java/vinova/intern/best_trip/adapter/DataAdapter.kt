package vinova.intern.best_trip.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import vinova.intern.best_trip.model.Taxi
import android.widget.TextView
import vinova.intern.best_trip.R
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
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
             itemView.setOnClickListener {
                 val intent = Intent (itemView.context, TaxiDetailActivity::class.java )
                 itemView.context.startActivity(intent)
             }
         }
     }

     fun setData(data: MutableList<Taxi?>) {
         taxiList.clear()
         taxiList = data
         notifyDataSetChanged()
     }

 }
