package com.task.board.controller.dto

data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String,
)
