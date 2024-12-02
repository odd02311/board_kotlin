package com.task.board.controller.dto

import com.task.board.service.dto.CommentResponseDto

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: String,
)

fun CommentResponseDto.toResponse() = CommentResponse(
    id = id,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt,
)
