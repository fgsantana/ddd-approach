package com.fgsantana.dddapproach.infrastructure.repository

import com.fgsantana.dddapproach.domain.entity.Product
import com.fgsantana.dddapproach.domain.repository.ProductRepositoryInterface
import com.fgsantana.dddapproach.infrastructure.db.hibernate.model.ProductModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class ProductRepository(val repo : ProductORMRepository) : ProductRepositoryInterface {
    override fun save(t: Product) {
        val productModel = ProductModel(t.id,t.name,t.price);
        repo.save(productModel);
    }

    override fun findById(id: Long): Product {
        val productFromDb = repo.findById(id).orElseThrow ( { -> RuntimeException("Product with id" + id + " not found!") } )
        return Product(productFromDb.id,productFromDb.name,productFromDb.price)
    }

    override fun findAll(): List<Product> = repo.findAll().map { productFromDb -> Product(productFromDb.id,productFromDb.name,productFromDb.price) }


    override fun update(t: Product){
        val productModel = ProductModel(t.id,t.name,t.price);
        repo.save(productModel);
    }

    override fun deleteById(id: Long)  = repo.deleteById(id)
}

@Repository
interface ProductORMRepository : CrudRepository<ProductModel, Long> {}
