package com.fgsantana.dddapproach.domain.product.factory

import com.fgsantana.dddapproach.domain.product.entity.Product
import com.fgsantana.dddapproach.domain.product.entity.ProductB
import com.fgsantana.dddapproach.domain.product.entity.ProductInterface


object ProductFactory {
    fun create(type: String, id: Long, name: String, price: Double): ProductInterface {
       val product =  when(type){
            "a" -> Product(id,name,price)
            "b" -> ProductB(id,name,price)
           else -> throw RuntimeException("")
        }

        return product;
    }
}