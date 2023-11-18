package permissions

/**
 *
 * CREATE -         создание нового блюда (админ)
 * READ_ALL -       чтение всех блюд (админ)
 * READ_PUBLIC-     чтение открытых для выбора блюд (все пользователи)
 * UPDATE -         обновление (админ)
 * DELETE -         удление (админ)
 * SEARCH_ALL -     поиск (админ)
 * SEARCH_PUBLIC -  поиск открытых для выбора блюд (все ользователи)
 *
 * */
@Suppress("unused")
enum class QrMenuUserPermissions {
    CREATE,
    READ_ALL,
    READ_PUBLIC,
    UPDATE,
    DELETE,
    SEARCH_ALL,
    SEARCH_PUBLIC,
}
