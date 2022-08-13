package com.fgsantana.dddapproach.domain.product.entity

class ProductB(var id: Long?, var name:String, var price:Double)  : ProductInterface {
    init { validate() }

    private fun validate() {
        if(this.id == null){
            throw RuntimeException("Id is required");
        }
        if(this.name.isEmpty()){
            throw RuntimeException("Name is required");
        }
        if(this.price <= 0.0){
            throw RuntimeException("Price must be greater than 0.0");
        }
    }
    override fun productPrice() = price * 2;
    override fun productName() = name
}