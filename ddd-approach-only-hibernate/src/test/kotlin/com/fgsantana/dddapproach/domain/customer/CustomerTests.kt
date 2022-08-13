package com.fgsantana.dddapproach.domain.customer

import com.fgsantana.dddapproach.domain.entity.Address
import com.fgsantana.dddapproach.domain.entity.Customer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CustomerTests {

	@Test
	fun testIfThrowAnErrorIfIdIsNotPresent() {
		Assertions.assertThrows(RuntimeException::class.java, { -> Customer(null , "Name") })
	}
	@Test
	fun testIfThrowAnErrorIfNameIsNotPresent() {
		Assertions.assertThrows(RuntimeException::class.java, { -> Customer(1 , "") })
	}

	@Test
	fun testIfNameIsChanged() {
		val customer = Customer(1 , "Name")
		val address = Address("Street", 1, "5524896","City")
		customer.changeAddress(address)

		customer.changeName("Changed Name")
		Assertions.assertEquals(customer.name, "Changed Name")
	}

	@Test
	fun testIfAddressIsChanged() {
		val customer = Customer(1 , "Name")
		val address = Address("Street", 1, "5524896","City")
		customer.changeAddress(address)

		Assertions.assertAll({ -> Assertions.assertEquals(customer.address!!.street, address.street)},
							 { -> Assertions.assertEquals(customer.address!!.number, address.number)},
							 { -> Assertions.assertEquals(customer.address!!.zip, address.zip)},
							 { -> Assertions.assertEquals(customer.address!!.city, address.city)} )
	}

	@Test
	fun testIfCustomerIsActivated() {
		val customer = Customer(1 , "Name")
		val address = Address("Street", 1, "5524896","City")
		customer.changeAddress(address)

		customer.activate()
		Assertions.assertTrue(customer.isActive())
	}

	@Test
	fun testIfCustomerIsDeactivated() {
		val customer = Customer(1 , "Name")
		val address = Address("Street", 1, "5524896","City")
		customer.changeAddress(address)

		customer.activate()
		Assertions.assertTrue(customer.isActive())

		customer.deactivate()
		Assertions.assertFalse(customer.isActive())
	}

	@Test
	fun testIfThrowAnErrorIfTryingToActivateACustomerWithoutAddress(){
		val customer = Customer(1 , "Name")

		Assertions.assertThrows(RuntimeException::class.java,{ -> customer.activate()})

		Assertions.assertFalse(customer.isActive())

	}




}
