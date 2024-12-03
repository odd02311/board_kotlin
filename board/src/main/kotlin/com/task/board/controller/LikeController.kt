package com.task.board.controller

import com.task.board.service.LikeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController(
    private val likeService: LikeService
) {
    @PostMapping("/posts/{postId}/likes")
    fun createLike(
        @PathVariable("postId") postId: Long,
        @RequestParam createdBy: String,
    ): Long {
        return likeService.createLike(postId, createdBy)
    }
}
