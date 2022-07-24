package com.fgsantana.dddapproach.domain.repository

import com.fgsantana.dddapproach.domain.entity.Product
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.ProductModel
import com.fgsantana.dddapproach.infrastructure.repository.ProductORMRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*


@SpringBootTest
class ProductRepositoryTests {

    @Autowired
    lateinit var repo: ProductRepositoryInterface

    @Autowired
    lateinit var repoORM: ProductORMRepository

    @BeforeEach
    fun beforeEach() = repoORM.deleteAll()

    @Test
    fun contextLoads() {}

    @Test
    fun testIfProductIsCreated() {
        val product = Product(12, "Product", 99.79)
        repo.save(product)

        val productFromDbOp: Optional<ProductModel> = repoORM.findById(12)
        Assertions.assertTrue(productFromDbOp.isPresent)

        val productFromdb = productFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(productFromdb,product))

    }

    @Test
    fun testIfProductIsUpdated() {
        val product = Product(12, "Product", 99.79)
        repo.save(product)

        val productFromDbOp: Optional<ProductModel> = repoORM.findById(12)
        Assertions.assertTrue(productFromDbOp.isPresent)

        val productFromDb = productFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(productFromDb,product))

        product.changeName("Changed Name")
        product.changePrice(85.99)
        repo.update(product)

        val productFromDbUpdatedOp: Optional<ProductModel> = repoORM.findById(12)
        Assertions.assertTrue(productFromDbUpdatedOp.isPresent)
        val productFromDbUpdated = productFromDbUpdatedOp.get();

        Assertions.assertTrue(assertModelEqualsToEntity(productFromDbUpdated,product))

    }

    @Test
    fun testIfProductIsFound() {
        val product = Product(12, "Product", 99.79)
        repo.save(product)

        val productFromRepo  = repo.findById(12)

        Assertions.assertTrue(productFromRepo == product)


    }

    @Test
    fun testIfThrowsAnErrorWhenProductIsNotFound() {
        Assertions.assertThrows(RuntimeException::class.java, {repo.findById(432)})
    }

    @Test
    fun testIfAllProductsAreFound() {
        val product1 = Product(12, "Product", 99.79)
        val product2 = Product(32, "Product 2", 105.99)

        val products = listOf(product1,product2)
        repo.save(product1)
        repo.save(product2)

        val productsFromRepo = repo.findAll();
        Assertions.assertTrue(products.size == 2 )

        Assertions.assertTrue(products.all { product -> productsFromRepo.contains(product) })

    }

    @Test
    fun testIfProductIsDeleted() {
        val product = Product(12, "Product", 99.79)
        repo.save(product)

        val productFromDbOp: Optional<ProductModel> = repoORM.findById(12)
        Assertions.assertTrue(productFromDbOp.isPresent)

        val productFromDb = productFromDbOp.get();
        Assertions.assertTrue(assertModelEqualsToEntity(productFromDb,product))

        repo.deleteById(12)

        val opAfterDeleted: Optional<ProductModel> = repoORM.findById(12)

        Assertions.assertTrue(opAfterDeleted.isEmpty)


    }

    private fun assertModelEqualsToEntity(productModel: ProductModel, product: Product) =
        productModel.id == product.id && productModel.name == product.name && productModel.price == (product.price)





    }





