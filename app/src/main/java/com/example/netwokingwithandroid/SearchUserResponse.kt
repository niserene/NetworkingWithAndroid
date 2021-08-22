package com.example.netwokingwithandroid

data class SearchUserResponse(
    val totalCount: Int?=null,
    val incompleteResults:Boolean?=null,
    val items: List<User>?=null
)