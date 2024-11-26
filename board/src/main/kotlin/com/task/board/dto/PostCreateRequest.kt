package com.task.board.dto

data class PostCreateRequest(
    val title:String,
    val content:String,
    val createdBy:String,
)
