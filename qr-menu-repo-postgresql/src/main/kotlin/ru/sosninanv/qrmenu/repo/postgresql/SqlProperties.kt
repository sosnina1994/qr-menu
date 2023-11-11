package ru.sosninanv.qrmenu.repo.postgresql

data class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/menu",
    val user: String = "postgres",
    val password: String = "password",
    val schema: String = "menu",
    val table: String = "dishes",
)
