package com.temp.springrediscache.domain.book.service

import com.temp.springrediscache.domain.book.entity.BookEntity
import java.math.BigDecimal

interface BookService {
    fun findBookByIsbn(isbn: String): BookEntity?
    fun findAllBooks(): List<BookEntity>
    fun saveBook(book: BookEntity): BookEntity
    fun updateBook(book: BookEntity): BookEntity
    fun deleteBook(isbn: String)
    fun findBooksByAuthor(author: String): List<BookEntity>
    fun searchBooksByTitle(keyword: String): List<BookEntity>
    fun findBooksByPublishYear(publishYear: Int?): List<BookEntity>
    fun findBooksByPriceRange(min: BigDecimal, max: BigDecimal): List<BookEntity>
    fun findCheapBooksByAuthor(author: String, max: BigDecimal): List<BookEntity>
}