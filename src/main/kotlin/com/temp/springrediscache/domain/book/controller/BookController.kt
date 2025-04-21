package com.temp.springrediscache.domain.book.controller

import com.temp.springrediscache.domain.book.entity.BookEntity
import com.temp.springrediscache.domain.book.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @GetMapping("/{isbn}")
    fun getBookByIsbn(@PathVariable isbn: String): ResponseEntity<BookEntity> {
        val findBook = bookService.findBookByIsbn(isbn)
        return findBook?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAllBooks(): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.findAllBooks())
    }

    @PostMapping
    fun createBook(@RequestBody book: BookEntity): ResponseEntity<BookEntity> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(bookService.saveBook(book))
    }

    @PutMapping("/{isbn}")
    fun updateBook(@PathVariable isbn: String, @RequestBody book: BookEntity): ResponseEntity<BookEntity> {
        if (isbn != book.isbn) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.updateBook(book))
    }

    @DeleteMapping("/{isbn}")
    fun deleteBook(@PathVariable isbn: String): ResponseEntity<Void> {
        bookService.deleteBook(isbn)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/author/{author}")
    fun getBooksByAuthor(@PathVariable author: String): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.findBooksByAuthor(author))
    }

    @GetMapping("/search")
    fun searchBooksByTitle(@RequestParam title: String): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.searchBooksByTitle(title))
    }

    @GetMapping("/year/{year}")
    fun getBooksByYear(@PathVariable year: Int?): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.findBooksByPublishYear(year))
    }

    @GetMapping("/price")
    fun getBooksByPriceRange(
        @RequestParam min: BigDecimal,
        @RequestParam max: BigDecimal
    ): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.findBooksByPriceRange(min, max))
    }

    @GetMapping("/author/{author}/price")
    fun getCheapBooksByAuthor(
        @PathVariable author: String,
        @RequestParam price: BigDecimal
    ): ResponseEntity<List<BookEntity>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.findCheapBooksByAuthor(author, price))
    }


}