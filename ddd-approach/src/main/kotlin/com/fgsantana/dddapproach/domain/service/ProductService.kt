package com.fgsantana.dddapproach.domain.service


import com.fgsantana.dddapproach.domain.entity.Product
import com.fgsantana.dddapproach.domain.repository.RepositoryInterface

import org.springframework.stereotype.Service



@Service
class ProductService(val repo: RepositoryInterface<Product>) {


    fun increasePrice(products: Array<Product>, amount: Int) {
        for (product in products) {
            product.changePrice(product.price + amount);

        }
    }





}