package com.temp.springrediscache.domain.book.repository

import com.temp.springrediscache.domain.book.entity.BookEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface BookRepository: JpaRepository<BookEntity, Long> {
    fun findByIsbn(isbn: String): BookEntity?
    fun findByAuthor(author: String): List<BookEntity>
    fun findByTitleContaining(keyword: String): List<BookEntity>
    fun findByPublishYear(publisher: Int?): List<BookEntity>
    fun findByPriceBetween(min: BigDecimal, max: BigDecimal): List<BookEntity>
    fun findByAuthorAndPublishYear(author: String, publisher: Int?): List<BookEntity>

    @Query("SELECT b FROM BookEntity b WHERE b.author = :author AND b.price < :price")
    fun findCheapBooksByAuthor(@Param("author") author: String, @Param("price") price: BigDecimal): List<BookEntity>


}