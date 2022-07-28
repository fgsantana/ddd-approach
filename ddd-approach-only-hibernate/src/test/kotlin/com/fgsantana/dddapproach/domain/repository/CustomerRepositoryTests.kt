package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.exception.NotFoundException
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.CustomerModel
import com.fgsantana.dddapproach.infrastructure.repository.CustomerRepository
import org.junit.jupiter.api.*
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceContext

class CustomerRepositoryTests {

    @PersistenceContext
    private val em: EntityManager =
        Persistence.createEntityManagerFactory("com.fgsantana.dddapproach").createEntityManager()

    private val repo =  CustomerRepository()

    @BeforeEach
    fun beforeEach(){
    em.transaction.begin()
    em.createQuery("delete from customer").executeUpdate()
    em.transaction.commit()
    em.clear()
    }

    @Test
    fun testIfCustomerIsCreated() {
        val address = Address("Street",143, "31263217", "City")
        val customer = Customer(11, "Name", address)

        repo.save(customer)

        val customerFromDb = em.find(CustomerModel::class.java, 11L)
        Assertions.assertNotNull(customerFromDb)

        Assertions.assertTrue(assertModelEqualsToEntity(customerFromDb,customer))

    }

    @Test
    fun testIfCustomerIsUpdated() {
        val address = Address("Street", 143, "31263217", "City")
        val customer = Customer(2, "Name",address)

        givenCustomerIsInDatabase(customer)
        em.clear()

        customer.changeName("Changed name")
        customer.changeAddress(Address("Changed street", 124, "31278217", "Changed city"))
        repo.update(customer)

        val customerFromDb = em.find(CustomerModel::class.java,2L)

        Assertions.assertTrue(assertModelEqualsToEntity(customerFromDb,customer))


    }

    @Test
    fun testIfCustomerIsFound() {
        val address = Address("Street",143, "31263217", "City")
        val customer = Customer(1, "Name",address)

        givenCustomerIsInDatabase(customer)
        em.clear()

        val customerFromRepo = repo.findById(1)
        val customerFromDb = em.find(CustomerModel::class.java,1L)

        Assertions.assertTrue(assertModelEqualsToEntity(customerFromDb,customerFromRepo))

    }

    @Test
    fun testIfThrowsAnErrorWhenCustomerIsNotFound() {
        Assertions.assertThrows(NotFoundException::class.java, {repo.findById(432)})
    }

    @Test
    fun testIfAllCustomersAreFound() {
        val address1 = Address("Street",143, "31263217", "City")
        val customer1 = Customer(12, "Name",address1)


        val address2 = Address("Street1",144, "3158947", "City1")
        val customer2 = Customer(32, "Name1",address2)


        givenCustomerIsInDatabase(customer1)
        givenCustomerIsInDatabase(customer2)



        val customersFromRepo = repo.findAll();
        val customersFromDb = em.createQuery("select c from customer c", CustomerModel::class.java).resultList
        Assertions.assertTrue(customersFromRepo.size == 2 )

        Assertions.assertTrue(customersFromRepo.containsAll(customersFromDb.map(this::modelToEntity)))

    }

    @Test
    fun testIfProductIsDeleted() {
        val address = Address("Street",143, "31263217", "City")
        val customer = Customer(12, "Name",address)

        givenCustomerIsInDatabase(customer)
        em.clear()

        repo.deleteById(12)

        val afterDelete = em.find(CustomerModel::class.java,12L)

        Assertions.assertNull(afterDelete)


    }

    private fun givenCustomerIsInDatabase(customer: Customer){
        val customerToSave = CustomerModel(customer.id,customer.name,customer.address!!,customer.active,customer.rewardPoints)
        em.transaction.begin()
        em.persist(customerToSave)
        em.transaction.commit()
    }

    private fun assertModelEqualsToEntity(customerModel: CustomerModel, customer: Customer) : Boolean{
        return customerModel.id == customer.id &&
                customerModel.name == customer.name &&
                customerModel.adress == customer.address &&
                customerModel.active == customer.active &&
                customerModel.rewardPoints == customer.rewardPoints
    }
    private fun modelToEntity(customerFromDb: CustomerModel) : Customer {

        val customerToReturn = Customer(customerFromDb.id, customerFromDb.name, customerFromDb.adress)
        customerToReturn.active = customerFromDb.active
        customerToReturn.rewardPoints = customerFromDb.rewardPoints
        return customerToReturn

    }
}
