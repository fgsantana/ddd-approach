package com.fgsantana.dddapproach.domain.service

import com.fgsantana.dddapproach.domain.entity.Product

class ProductService() {



    fun increasePrice(products: Array<Product>, amount: Int) {
        for (product in products) {
            product.changePrice(product.price + amount);

        }
    }
}