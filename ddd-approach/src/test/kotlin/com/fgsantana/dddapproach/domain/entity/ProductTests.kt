package com.fgsantana.dddapproach.domain.entity

import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProductTests {

    @Test
    fun contextLoads(){

    }

    @Test
    fun testIfThrowAnErrorIfIdIsNotPresent(){
        Assertions.assertThrows(RuntimeException::class.java, { -> Product(null, "Name", 98.99);})
    }

    @Test
    fun testIfThrowAnErrorIfPriceIsIsNotGreaterThanZero(){
        Assertions.assertThrows(RuntimeException::class.java, { -> Product(1,"Name", 0.0) })
    }

    @Test
    fun testIfThrowAnErrorIfNameIsNotPresent(){
        Assertions.assertThrows(RuntimeException::class.java, { -> Product(1, "", 89.99) })
    }

    @Test
    fun testIfNameIsChanged() {

        val product = Product(1, "Name", 89.99)


        product.changeName("Changed Name")
        Assertions.assertEquals(product.name, "Changed Name")
    }


}