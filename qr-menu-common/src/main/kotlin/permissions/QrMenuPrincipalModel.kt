package permissions

import models.QrMenuUserId

data class QrMenuPrincipalModel(
    val id: QrMenuUserId = QrMenuUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<QrMenuUserGroups> = emptySet()
) {
    companion object {
        val NONE = QrMenuPrincipalModel()
    }
}
