package com.task.board.controller.dto

import com.task.board.service.dto.PostDetailResponseDto

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val comments: List<CommentResponse> = emptyList(),
)

fun PostDetailResponseDto.toResponse() = PostDetailResponse(
    id = id,
    title = title,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt,
)
