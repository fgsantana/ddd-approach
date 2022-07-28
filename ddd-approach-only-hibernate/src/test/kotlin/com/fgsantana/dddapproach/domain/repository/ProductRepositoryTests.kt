package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Product
import com.fgsantana.dddapproach.exception.NotFoundException
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.ProductModel
import com.fgsantana.dddapproach.infrastructure.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceContext


class ProductRepositoryTests{

    @PersistenceContext
    private val em: EntityManager =
        Persistence.createEntityManagerFactory("com.fgsantana.dddapproach").createEntityManager()

    private val repo =  ProductRepository()

    @BeforeEach
    fun beforeEach()  {
        em.transaction.begin()
        em.createQuery("delete from product").executeUpdate()
        em.transaction.commit()
        em.clear()
    }

    @Test
    fun testIfProductIsCreated() {
        val product = Product(12, "Product", 99.79)
        repo.save(product)

        val productFromDb = em.find(ProductModel::class.java,12L);
        Assertions.assertTrue(productFromDb != null)

        Assertions.assertTrue(assertModelEqualsToEntity(productFromDb,product))

    }

    @Test
    fun testIfProductIsUpdated() {
        val product = Product(12, "Product", 99.79)

        givenProductIsInDatabase(product)
        em.clear()

        product.changeName("Changed Name")
        product.changePrice(85.99)

        repo.update(product)
        val productFromDbUpdated = em.find(ProductModel::class.java,12L)
        Assertions.assertTrue(productFromDbUpdated != null)

        Assertions.assertTrue(assertModelEqualsToEntity(productFromDbUpdated,product))

    }

    @Test
    fun testIfProductIsFound() {
        val product = Product(12, "Product", 99.79)

        givenProductIsInDatabase(product)
        em.clear()

        val productFromRepo = repo.findById(12)
        val productFromDb = em.find(ProductModel::class.java,12L)
        Assertions.assertTrue(assertModelEqualsToEntity(productFromDb,productFromRepo))

    }

    @Test
    fun testIfThrowsAnErrorWhenProductIsNotFound() {
        Assertions.assertThrows(NotFoundException::class.java, {repo.findById(432)})
    }

    @Test
    fun testIfAllProductsAreFound() {
        val product1 = Product(12, "Product", 99.79)
        val product2 = Product(32, "Product 2", 105.99)


        givenProductIsInDatabase(product1)
        givenProductIsInDatabase(product2)
        em.clear()

        val productsFromRepo = repo.findAll()
        val productsFromDb = em.createQuery("select p from product p", ProductModel::class.java).resultList

        Assertions.assertTrue(productsFromRepo.size == 2 )

        Assertions.assertTrue(productsFromRepo.containsAll(productsFromDb.map (this::modelToEntity)  ))

    }

    @Test
    fun testIfProductIsDeleted() {
        val product = Product(12, "Product", 99.79)

        givenProductIsInDatabase(product)
        em.clear()

        repo.deleteById(12)

        val afterDelete= em.find(ProductModel::class.java,12L)

        Assertions.assertNull(afterDelete)


    }

    private fun givenProductIsInDatabase(product: Product) {
        em.transaction.begin()
        em.persist(ProductModel(product.id, product.name, product.price))
        em.transaction.commit()
    }

    private fun assertModelEqualsToEntity(productModel: ProductModel, product: Product) =
        productModel.id == product.id && productModel.name == product.name && productModel.price == (product.price)

    private fun modelToEntity(productFromDb: ProductModel) : Product = Product(productFromDb.id,productFromDb.name, productFromDb.price)
        
}

