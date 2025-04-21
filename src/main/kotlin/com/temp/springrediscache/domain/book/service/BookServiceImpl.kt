package com.temp.springrediscache.domain.book.service

import com.temp.springrediscache.domain.book.entity.BookEntity
import com.temp.springrediscache.domain.book.repository.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal


@Service
class BookServiceImpl(
    private val bookRepository: BookRepository
): BookService {

    companion object {
        private val logger = LoggerFactory.getLogger(BookServiceImpl::class.java)
    }

    override fun findBookByIsbn(isbn: String): BookEntity? {
        logger.info("책 정보를 조회합니다: ${isbn}")
        return bookRepository.findByIsbn(isbn)
    }

    override fun findAllBooks(): List<BookEntity> {
        logger.info("모든 책 정보를 조회합니다.")
        return bookRepository.findAll()
    }

    override fun saveBook(book: BookEntity): BookEntity {
        logger.info("책 정보를 저장합니다: ${book.isbn}")
        return bookRepository.save(book)
    }

    @Transactional
    override fun updateBook(book: BookEntity): BookEntity {
        logger.info("책 정보를 업데이트 합니다: ${book.isbn}")
        val existingBook = bookRepository.findByIsbn(book.isbn) ?:
            throw IllegalArgumentException("책을 찾을 수 없습니다: ${book.isbn}")

        return existingBook.apply {
            title = book.title
            author = book.author
            price = book.price
            publishYear = book.publishYear
            description = book.description
        }
    }

    override fun deleteBook(isbn: String) {
        logger.info("책 정보를 삭제합니다: ${isbn}")
        val findBook = bookRepository.findByIsbn(isbn)
            ?: throw IllegalArgumentException("책을 찾을 수 없습니다: ${isbn}")

        bookRepository.delete(findBook)
    }

    override fun findBooksByAuthor(author: String): List<BookEntity> {
        logger.info("저자를 기준으로 책을 조회합니다.: ${author}")
        return bookRepository.findByAuthor(author)
    }

    override fun searchBooksByTitle(keyword: String): List<BookEntity> {
        logger.info("제목을 기준으로 책을 조회합니다.: ${keyword}")
        return bookRepository.findByTitleContaining(keyword)
    }

    override fun findBooksByPublishYear(publishYear: Int?): List<BookEntity> {
        logger.info("출판 연도를 기준으로 책을 조회합니다.: ${publishYear}")
        return bookRepository.findByPublishYear(publishYear)
    }

    override fun findBooksByPriceRange(min: BigDecimal, max: BigDecimal): List<BookEntity> {
        logger.info("가격 범위를 기준으로 책을 조회합니다.: ${min} ~ ${max}")
        return bookRepository.findByPriceBetween(min, max)
    }

    override fun findCheapBooksByAuthor(author: String, max: BigDecimal): List<BookEntity> {
        logger.info("저자와 가격을 기준으로 책을 조회합니다.: ${author} / ${max}")
        return bookRepository.findCheapBooksByAuthor(author, max)
    }
}