package com.task.board.service.dto

import com.task.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val firstTag: String? = null,
    val likeCount: Long = 0,
)

fun Page<Post>.toSummaryResponseDto(countLike: (Long) -> Long) = PageImpl( // page에 대한 확장 함수
    content.map { it.toSummaryResponseDto(countLike) },
    pageable,
    totalElements,
)

fun Post.toSummaryResponseDto(countLike: (Long) -> Long) = PostSummaryResponseDto(   // post를 summary로 만드는 확장함수
    id = id,
    title = title,
    createdBy = createdBy,
    createdAt = createdAt.toString(),
    firstTag = tags.firstOrNull()?.name,
    likeCount = countLike(id)

)
