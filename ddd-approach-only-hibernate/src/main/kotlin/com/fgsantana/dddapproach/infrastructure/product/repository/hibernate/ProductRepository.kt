package com.fgsantana.dddapproach.infrastructure.product.repository.hibernate

import com.fgsantana.dddapproach.domain.product.entity.Product
import com.fgsantana.dddapproach.domain.product.repository.ProductRepositoryInterface
import com.fgsantana.dddapproach.exception.NotFoundException
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceContext
import javax.transaction.Transactional


class ProductRepository: ProductRepositoryInterface {

    @PersistenceContext
    private var em: EntityManager =
        Persistence.createEntityManagerFactory("com.fgsantana.dddapproach").createEntityManager()

    @Transactional
    override fun save(t: Product) {
        val productModel = ProductModel(t.id,t.name,t.price);
        em.transaction.begin()
        em.persist(productModel)
        em.transaction.commit()
    }

    override fun findById(id: Long): Product {
        val productFromDb = em.find(ProductModel::class.java,id)
            ?: throw NotFoundException("Product with id $id not found!")

        return Product(productFromDb.id,productFromDb.name,productFromDb.price)
    }

    override fun findAll(): List<Product> {
        val productsFromDb : List<ProductModel> = em.createQuery("select p from product p", ProductModel::class.java).resultList
        return productsFromDb.map { productFromDb -> Product(productFromDb.id,productFromDb.name,productFromDb.price) }
    }

    override fun update(t: Product) {
        val productModel = ProductModel(t.id,t.name,t.price)
        em.transaction.begin()
        em.merge(productModel)
        em.transaction.commit()

    }

    override fun deleteById(id: Long) {
        em.transaction.begin()
        em.createQuery("delete from product where id=:id").setParameter("id",id).executeUpdate()
        em.transaction.commit()

    }
}
