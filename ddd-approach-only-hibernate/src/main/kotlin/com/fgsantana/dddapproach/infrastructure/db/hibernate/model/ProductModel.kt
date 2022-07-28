package com.fgsantana.dddapproach.infrastructure.db.hibernate.model

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