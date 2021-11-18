package com.marcos.data.repository

import com.marcos.data.source.LocationDataSource

class RegionRepository(private val locationDataSource: LocationDataSource, private  val permissionChecker: PermissionChecker) {
    companion object{
        private const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion () : String {
        return if (permissionChecker.check(PermissionChecker.Permision.COARSE_LOCATION)){
            locationDataSource.findLastRegion()?: DEFAULT_REGION
        }else{
            DEFAULT_REGION
        }
    }
}

interface PermissionChecker {
    enum class Permision {COARSE_LOCATION}
    suspend fun check(permission: Permision): Boolean
}