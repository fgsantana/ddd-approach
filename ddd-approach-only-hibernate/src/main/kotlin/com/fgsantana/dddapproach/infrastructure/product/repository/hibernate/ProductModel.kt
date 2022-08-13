package com.fgsantana.dddapproach.infrastructure.product.repository.hibernate

import javax.persistence.*

@Entity(name = "product")
data class ProductModel(
    @Id
    var id: Long?=null,

    @Column
    var name : String,

    @Column
    var price : Double,

    ) {}