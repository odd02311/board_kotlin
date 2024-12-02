package com.task.board.domain

import com.task.board.exception.CommentNotUpdatableException
import com.task.board.service.dto.CommentUpdateRequestDto
import jakarta.persistence.*

@Entity
class Comment(
    content: String,
    post: Post,
    createdBy: String,
): BaseEntity(createdBy = createdBy) {

    fun update(updateRequestDto: CommentUpdateRequestDto) {
        if(updateRequestDto.updatedBy != this.createdBy){
            throw CommentNotUpdatableException()
        }
        this.content = updateRequestDto.content
        super.updatedBy(updateRequestDto.updatedBy)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        protected set
}
