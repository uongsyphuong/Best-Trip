package vinova.intern.best_trip.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import vinova.intern.best_trip.model.Taxi
import android.widget.TextView
import vinova.intern.best_trip.R
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import java.util.ArrayList

class DataAdapter: RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    var search: String = ""
    lateinit var arrayList: ArrayList<String>

    lateinit var context:Context

    constructor(context: Context, arrayList:ArrayList<String> ) {
        this.context = context
        this.arrayList = arrayList
        this.search = ""
     }
    constructor(context: Context){
        this.context = context
    }

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

         lateinit var image_taxi: ImageView
         lateinit var phone_taxi: TextView
         lateinit var name_taxi: TextView

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

     fun notifyDataSetChangedWithSearch(search: String) {
         this.search = search
         super.notifyDataSetChanged()
     }
 }
