package com.task.board.service.dto

import com.task.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
)

fun Page<Post>.toSummaryResponseDto() = PageImpl( // page에 대한 확장 함수
    content.map { it.toSummaryResponseDto() },
    pageable,
    totalElements,
)

fun Post.toSummaryResponseDto() = PostSummaryResponseDto(   // post를 summary로 만드는 확장함수
    id = id,
    title = title,
    createdBy = createdBy,
    createdAt = createdAt.toString(),
)
