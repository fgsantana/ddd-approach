package com.fgsantana.dddapproach.infrastructure.db.hibernate.model

import com.fgsantana.dddapproach.domain.entity.Address
import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.Embedded

@Entity(name = "customer")
data class CustomerModel (
    @Id
    val id: Long?,

    @Column
    val name: String,

    @Embedded
    val adress: Address,

    @Column
    val active: Boolean,

    @Column(name ="reward_points")
    val rewardPoints: Double,
    ) {}
