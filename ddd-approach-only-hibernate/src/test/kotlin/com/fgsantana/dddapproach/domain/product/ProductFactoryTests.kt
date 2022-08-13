package com.fgsantana.dddapproach.domain.product

import com.fgsantana.dddapproach.domain.product.entity.Product
import com.fgsantana.dddapproach.domain.product.entity.ProductB
import com.fgsantana.dddapproach.domain.product.factory.ProductFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ProductFactoryTests {

    @Test
    fun testIfProductAIsCreated(){
        val product = ProductFactory.create("a", 4551,"Product",10.0);
        Assertions.assertEquals(product.productName(), "Product")
        Assertions.assertEquals(product.productPrice(), 10.0)
        Assertions.assertTrue(product is Product)
    }
    @Test
    fun testIfProductBIsCreated(){
        val product = ProductFactory.create("b", 4551,"Product",10.0);
        Assertions.assertEquals(product.productName(), "Product")
        Assertions.assertEquals(product.productPrice(), 20.0)
        Assertions.assertTrue(product is ProductB)
    }

    @Test
    fun testIfErrorIfTypeNotSupported(){
        Assertions.assertThrows(RuntimeException::class.java, {-> ProductFactory.create("c", 4551,"Product",10.0)})

    }
}