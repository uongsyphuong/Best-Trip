package vinova.intern.best_trip.model

data class Taxi(val name:String, val phone:String, val logo:String, val about:String, val fee:List<Fee>)
data class Fee(val first:Double, val after:Double)