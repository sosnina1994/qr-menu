package ru.sosninanv.qrmenu.repo.postgresql

import RepoDishCreateTest
import RepoDishDeleteTest
import RepoDishReadTest
import RepoDishSearchTest
import RepoDishUpdateTest
import repo.IDishRepository
import kotlin.random.Random

val random = Random(System.currentTimeMillis())

class RepoDishSQLCreateTest : RepoDishCreateTest() {
    override val repo: IDishRepository = SqlTestCompanion.repoUnderTestContainer(
        "create-" + random.nextInt(),
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLReadTest : RepoDishReadTest() {
    override val repo: IDishRepository = SqlTestCompanion.repoUnderTestContainer(
        "read" + random.nextInt(),
        initObjects
    )
}

class RepoAdSQLDeleteTest : RepoDishDeleteTest() {
    override val repo: IDishRepository = SqlTestCompanion.repoUnderTestContainer(
        "delete_" + random.nextInt(),
        initObjects
    )
}

class RepoAdSQLUpdateTest : RepoDishUpdateTest() {
    override val repo: IDishRepository = SqlTestCompanion.repoUnderTestContainer(
        "update" + random.nextInt(),
        initObjects,
        randomUuid = { lockNew.asString() },
    )
}

class RepoAdSQLSearchTest : RepoDishSearchTest() {
    override val repo: IDishRepository = SqlTestCompanion.repoUnderTestContainer(
        "search" + random.nextInt(),
        initObjects
    )
}


