package com.fgsantana.dddapproach.domain.service;



import com.fgsantana.dddapproach.domain.entity.Product
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test



class ProductServiceTests {


    private val service = ProductService();


    @Test
    fun testIfServiceIncreasePriceOfAllProducts(){
        val product1  = Product(32,"Product 1", 99.99)
        val product2  = Product(326,"Product 2", 199.99)
        val product3  = Product(334,"Product 3", 89.99)


        service.increasePrice(arrayOf(product1,product2,product3),200);
        Assertions.assertEquals(product1.price, 299.99 )
        Assertions.assertEquals(product2.price, 399.99 )
        Assertions.assertEquals(product3.price, 289.99 )
    }
}