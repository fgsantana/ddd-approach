package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.CustomerModel
import com.fgsantana.dddapproach.infrastructure.repository.CustomerORMRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class CustomerRepositoryTests {

    @Autowired
    lateinit var repo: CustomerRepositoryInterface

    @Autowired
    lateinit var repoORM: CustomerORMRepository


    @BeforeEach
    fun beforeEach() = repoORM.deleteAll()

    @Test
    fun contextLoads() {}

    @Test
    fun testIfCustomerIsCreated() {
        val customer = Customer(11, "Name")
        val address = Address("Street",143, "31263217", "City")
        customer.changeAddress(address)
        repo.save(customer)

        val customerFromDbOp: Optional<CustomerModel> = repoORM.findById(11)
        Assertions.assertTrue(customerFromDbOp.isPresent)

        val customerFromdb = customerFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(customerFromdb,customer))

    }

    @Test
    fun testIfCustomerIsUpdated() {
        val customer = Customer(2, "Name")
        val address = Address("Street",143, "31263217", "City")
        customer.changeAddress(address)
        repo.save(customer)

        val customerFromDbOp: Optional<CustomerModel> = repoORM.findById(2)
        Assertions.assertTrue(customerFromDbOp.isPresent)

        val customerFromDb = customerFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(customerFromDb,customer))

        val updatedAddress = Address("Street 1",582, "3126", "City 1")
        customer.changeName("Changed Name")
        customer.changeAddress(updatedAddress)
        customer.activate()

        repo.update(customer)

        val customerFromDbUpdatedOp: Optional<CustomerModel> = repoORM.findById(2)
        Assertions.assertTrue(customerFromDbUpdatedOp.isPresent)
        val customerFromDbUpdated = customerFromDbUpdatedOp.get();

        Assertions.assertTrue(assertModelEqualsToEntity(customerFromDbUpdated,customer))

    }

    @Test
    fun testIfCustomerIsFound() {
        val customer = Customer(1, "Name")
        val address = Address("Street",143, "31263217", "City")
        customer.changeAddress(address)
        repo.save(customer)

        val customerFromRepo = repo.findById(1)

        Assertions.assertTrue(customerFromRepo == customer)

    }

    @Test
    fun testIfThrowsAnErrorWhenCustomerIsNotFound() {
        Assertions.assertThrows(RuntimeException::class.java, {repo.findById(432)})
    }

    @Test
    fun testIfAllCustomersAreFound() {
        val address1 = Address("Street",143, "31263217", "City")
        val customer1 = Customer(12, "Name")
        customer1.changeAddress(address1)

        val address2 = Address("Street1",144, "3158947", "City1")
        val customer2 = Customer(32, "Name1")
        customer2.changeAddress(address2)

        val customers = listOf(customer1,customer2)
        repo.save(customer1)
        repo.save(customer2)


        val customersFromRepo = repo.findAll();
        Assertions.assertTrue(customersFromRepo.size == 2 )

        Assertions.assertTrue(customers.all { customer -> customersFromRepo.contains(customer) })

    }

    @Test
    fun testIfProductIsDeleted() {
        val address = Address("Street",143, "31263217", "City")
        val customer = Customer(12, "Name")
        customer.changeAddress(address)
        repo.save(customer)

        val customerFromDbOp: Optional<CustomerModel> = repoORM.findById(12)
        Assertions.assertTrue(customerFromDbOp.isPresent)

        val customerFromDb = customerFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(customerFromDb,customer))

        repo.deleteById(12)

        val opAfterDeleted: Optional<CustomerModel> = repoORM.findById(12)

        Assertions.assertTrue(opAfterDeleted.isEmpty)


    }



    private fun assertModelEqualsToEntity(customerModel: CustomerModel, customer: Customer) : Boolean{
        return customerModel.id == customer.id &&
                customerModel.name == customer.name &&
                customerModel.street == customer.address!!.street &&
                customerModel.number == customer.address!!.number &&
                customerModel.zip == customer.address!!.zip &&
                customerModel.city == customer.address!!.city &&
                customerModel.active == customer.active &&
                customerModel.rewardPoints == customer.rewardPoints
    }
}