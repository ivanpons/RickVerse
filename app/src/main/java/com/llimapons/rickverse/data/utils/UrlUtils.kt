package com.llimapons.rickverse.data.utils

fun getUrlIds(urlList: List<String>?): List<String> {
    return urlList?.map { url ->
        url.substringAfterLast("/")
    } ?: emptyList()
}