package exceptions

import models.QrMenuDishLock

class RepoConcurrencyException(expectedLock: QrMenuDishLock, actualLock: QrMenuDishLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
