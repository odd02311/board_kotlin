package com.task.board.repository

import com.task.board.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
}
