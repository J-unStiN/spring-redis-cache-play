package com.temp.springrediscache.domain.book.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.math.BigDecimal

@Entity
@Table(name = "books")
class BookEntity(
    @Id
    val isbn: String,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var author: String,

    @Column(precision = 10, scale = 2)
    var price: BigDecimal? = null,

    @Column
    var publishYear: Int? = null,

    @Column(length = 2000)
    var description: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

    constructor() : this(
        isbn = "",
        title = "",
        author = "",
    )
}