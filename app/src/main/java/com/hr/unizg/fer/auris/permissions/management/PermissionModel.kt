package com.hr.unizg.fer.auris.permissions.management

data class PermissionModel(val permissionList: Array<String>, val requestCode: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PermissionModel

        if (!permissionList.contentEquals(other.permissionList)) return false
        if (requestCode != other.requestCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = permissionList.contentHashCode()
        result = 31 * result + requestCode
        return result
    }
}