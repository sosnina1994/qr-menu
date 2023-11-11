package ru.sosninanv.qrmenu.repo.postgresql

data class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/qrmenu",
    val user: String = "postgres",
    val password: String = "qrmenupg",
    val schema: String = "qr-menu",
    val table: String = "t_dishes",
)
