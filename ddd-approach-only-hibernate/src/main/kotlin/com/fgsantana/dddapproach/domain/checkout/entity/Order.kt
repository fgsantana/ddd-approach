package com.fgsantana.dddapproach.domain.checkout.entity


data class Order(val id: Long?, var customerId: Long?, var items: MutableList<OrderItem>) {
    init { validate() }

    private fun validate(){
        if(id==null){
            throw RuntimeException("Id is required");
        }
        if(this.customerId == null){
            throw RuntimeException("CustomerId is required");
        }
        if(this.items.isEmpty()){
            throw RuntimeException("Items list cannot be empty");
        }
        if(this.items.any { item -> item.price <= 0.0 }){
            throw RuntimeException("Items quantity must be greater than zero");
        }

    }

    fun total(): Double{
        var total = 0.0
        items.forEach { item -> total += item.price() }
        return total
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        if (id != other.id) return false
        if (customerId != other.customerId) return false
        if(other.items.size != items.size) return false
        if (!items.containsAll(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (customerId?.hashCode() ?: 0)
        result = 31 * result + items.hashCode()
        return result
    }


}

data class OrderItem (val id: Long, var name: String, var price: Double, var productId: Long, var quantity: Int){


    init{ validate() }

    private fun validate() {
        if(this.price <=0){
            throw RuntimeException("Item price must be greater than zero")
        }
    }

    fun price(): Double{
        return this.quantity * this.price;
    }
}