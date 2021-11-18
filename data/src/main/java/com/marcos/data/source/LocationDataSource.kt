package com.marcos.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}