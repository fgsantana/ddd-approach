package com.fgsantana.dddapproach.infrastructure.customer.repository.hibernate

import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.customer.repository.CustomerRepositoryInterface
import com.fgsantana.dddapproach.exception.NotFoundException
import javax.persistence.EntityManager
import javax.persistence.Persistence

class CustomerRepository : CustomerRepositoryInterface {

    val em: EntityManager =
        Persistence.createEntityManagerFactory("com.fgsantana.dddapproach").createEntityManager()


     override fun save(t:Customer) {
        em.transaction.begin()
        val customerToSave = CustomerModel(t.id,t.name,t.address!!,t.active,t.rewardPoints)
        em.persist(customerToSave)
        em.transaction.commit()
    }

    override fun findById(id: Long): Customer {
        val customerFromDb = em.find(CustomerModel::class.java, id)
            ?: throw NotFoundException("Customer with id $id not found!")
        return toEntity(customerFromDb)
    }


    override fun findAll(): List<Customer> {
        val customersFromDb = em.createQuery("select c from customer c", CustomerModel::class.java).resultList
        return customersFromDb.map { customer ->  Customer(customer.id, customer.name, customer.adress) }
        }



    override fun update(t: Customer) {
        em.transaction.begin()
        em.merge(CustomerModel(t.id,t.name,t.address!!,t.active,t.rewardPoints))
        em.transaction.commit()
    }

    override fun deleteById(id: Long) {
        em.transaction.begin()
        em.createQuery("delete from customer where id=:id").setParameter("id",id).executeUpdate()
        em.transaction.commit()
    }

        private fun toEntity(customerModel: CustomerModel): Customer {
            val customerToReturn = Customer(customerModel.id, customerModel.name, customerModel.adress)
            customerToReturn.active = customerModel.active
            customerToReturn.rewardPoints = customerModel.rewardPoints
            return customerToReturn

        }

}
