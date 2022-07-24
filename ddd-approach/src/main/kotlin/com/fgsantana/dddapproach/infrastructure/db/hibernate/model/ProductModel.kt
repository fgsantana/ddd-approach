package com.fgsantana.dddapproach.infrastructure.db.hibernate.model

import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.Column

@Entity(name = "product")
class ProductModel(
    @Id
    val id: Long?=null,

    @Column
    val name : String,

    @Column
    val price : Double,

    ) {}