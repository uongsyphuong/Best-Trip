package vinova.intern.best_trip.model

class Taxi{
    var name: String? = null
    val phone:String = ""
    val logo:String = ""
    var about:String = ""
    var fourSeaters: HashMap<String,Long>? =null
    var sevenSeaters: HashMap<String,Long>? = null

    constructor()

}

class Fee{
    var open_door:Int = 0
    var first:Int = 0
    var after:Int = 0
    constructor(){}
    constructor(open_door: Int, first: Int, after: Int) {
        this.open_door = open_door
        this.first = first
        this.after = after
    }

}
