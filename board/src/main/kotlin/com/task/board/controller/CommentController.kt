package com.task.board.controller

import com.task.board.controller.dto.CommentCreateRequest
import com.task.board.controller.dto.CommentUpdateRequest
import org.springframework.web.bind.annotation.*

@RestController
class CommentController {
    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest,
    ): Long{
        println(commentCreateRequest.content)
        println(commentCreateRequest.createdBy)
        return 1L
    }

    @PutMapping("/comments/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest,
    ): Long{
        println(commentUpdateRequest.content)
        println(commentUpdateRequest.updatedBy)
        return commentId
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
        @RequestParam deletedBy: String,
    ): Long{
        println(deletedBy)
        return commentId
    }
}
