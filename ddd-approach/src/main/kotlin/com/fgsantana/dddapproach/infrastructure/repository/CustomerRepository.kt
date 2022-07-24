package com.fgsantana.dddapproach.infrastructure.repository

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.domain.repository.CustomerRepositoryInterface
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.CustomerModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class CustomerRepository (val repo: CustomerORMRepository) : CustomerRepositoryInterface {

    override fun save(t:Customer) {
        val customerToSave = CustomerModel(t.id,t.name,t.address!!.street,t.address!!.number,t.address!!.zip,t.address!!.city,t.active,t.rewardPoints)
        repo.save(customerToSave)
    }

    override fun findById(id: Long): Customer {
        val customerFromDb = repo.findById(id).orElseThrow ( { -> RuntimeException("Customer with id" + id + " not found!") } )
        return toEntity(customerFromDb)
    }

    override fun findAll(): List<Customer> = repo.findAll().map { customerFromDb -> toEntity(customerFromDb) }

    override fun update(t:Customer){
        val customerToSave = CustomerModel(t.id,t.name,t.address!!.street,t.address!!.number,t.address!!.zip,t.address!!.city,t.active,t.rewardPoints)
        repo.save(customerToSave)
    }

    override fun deleteById(id: Long) = repo.deleteById(id)

    private fun toEntity(customerModel: CustomerModel) : Customer{
        val customer = Customer(customerModel.id,customerModel.name);
        val address  = Address(customerModel.street,customerModel.number,customerModel.zip,customerModel.city);
        customer.changeAddress(address)
        return customer;
    }

}

@Repository
interface CustomerORMRepository : CrudRepository<CustomerModel, Long>{}
