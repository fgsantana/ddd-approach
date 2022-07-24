package com.fgsantana.dddapproach.domain.entity

data class Customer(val id: Long?, var name: String){
    var address: Address? = null

    var active: Boolean = false

    var rewardPoints: Double = 0.0

    init { validate() }

    private fun validate(){
        if(name.isEmpty()){
            throw RuntimeException("Name cannot be empty!")
        }
        if(id == null){
            throw RuntimeException("Id cannot be empty!")
        }
    }

    fun changeName(name: String){
        this.name = name
        validate()
    }

    fun changeAddress(address: Address){ this.address = address }

    fun activate(){
        if(this.address==null){
            throw RuntimeException("Customer must have an adress to be activated")
        }
        this.active=true
    }
    fun deactivate(){ this.active=false }

    fun addRewardPoints(points: Double){ this.rewardPoints+=points }

    fun isActive(): Boolean = this.active

}

data class Address(var street: String, var number: Int?,  var zip: String,  var city: String)  {
    init{ validate() }

    private fun validate() {
        if(this.street.isEmpty()){
            throw RuntimeException("Street is required")
        }
        if(this.number == null){
            throw RuntimeException("Number is required")
        }
        if(this.street.isEmpty()){
            throw RuntimeException("Street is required")
        }
        if(this.street.isEmpty()){
            throw RuntimeException("Street is required")
        }

    }

}