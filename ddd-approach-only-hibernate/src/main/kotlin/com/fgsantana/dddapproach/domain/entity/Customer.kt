package com.fgsantana.dddapproach.domain.entity

import javax.persistence.Embeddable

data class Customer(val id: Long?, var name: String,var address: Address?=null){

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (name != other.name) return false
        if (address != other.address) return false
        if (active != other.active) return false
        if (rewardPoints != other.rewardPoints) return false

        return true
    }
}

@Embeddable
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