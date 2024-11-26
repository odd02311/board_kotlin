package com.task.board.service.dto

import com.task.board.domain.Post

data class PostCreateRequestDto(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun PostCreateRequestDto.toEntity() = Post(
    title = title,
    content = content,
    createdBy = createdBy,
)
