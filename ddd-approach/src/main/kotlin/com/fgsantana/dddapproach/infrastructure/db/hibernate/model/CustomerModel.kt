package com.fgsantana.dddapproach.infrastructure.db.hibernate.model

import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.Column

@Entity(name = "customer")
data class CustomerModel (
    @Id
    val id: Long?,

    @Column
    val name: String,

    @Column
    val street: String,

    @Column
    val number: Int?,

    @Column
    val zip: String,

    @Column
    val city: String,

    @Column
    val active: Boolean,

    @Column(name ="reward_points")
    val rewardPoints: Double,
    )