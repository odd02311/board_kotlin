package com.task.board.controller.dto

import com.task.board.service.dto.CommentCreateRequestDto

data class CommentCreateRequest(
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequest.toDto() = CommentCreateRequestDto(
    content = content,
    createdBy = createdBy,
)
