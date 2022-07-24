package com.fgsantana.dddapproach.domain.repository


interface RepositoryInterface <T> {
    fun save(t: T);

    fun findById(id: Long) : T;

    fun findAll(): List<T>;

    fun update(t: T);

    fun deleteById(id: Long);
}