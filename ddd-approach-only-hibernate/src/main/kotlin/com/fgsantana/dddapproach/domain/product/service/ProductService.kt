package com.fgsantana.dddapproach.domain.product.service

import com.fgsantana.dddapproach.domain.product.entity.Product

class ProductService() {



    fun increasePrice(products: Array<Product>, amount: Int) {
        for (product in products) {
            product.changePrice(product.price + amount);

        }
    }
}