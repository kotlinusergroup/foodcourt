package com.kotlinusergroup.ulfoodcourt.models

/**
 * Created by ajithvgiri on 07/11/17.
 */

class FoodMenu {
    var id: Int = 0
    var name: String = ""
    var price: Int = 0
    var status: Int = 0


    constructor(id: Int, name: String, price: Int, status: Int) {
        this.id = id
        this.name = name
        this.price = price
        this.status = status
    }

    constructor()


}

