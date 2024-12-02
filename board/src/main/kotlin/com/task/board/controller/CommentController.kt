package com.task.board.controller

import com.task.board.controller.dto.CommentCreateRequest
import com.task.board.controller.dto.CommentUpdateRequest
import com.task.board.controller.dto.toDto
import com.task.board.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest,
    ): Long{
        return commentService.createComment(postId, commentCreateRequest.toDto())
    }

    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest,
    ): Long{
        return commentService.updateComment(commentId, commentUpdateRequest.toDto())
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String,
    ): Long{
        return commentService.deleteComment(commentId, deletedBy)
    }
}
