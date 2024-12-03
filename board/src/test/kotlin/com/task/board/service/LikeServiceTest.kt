package com.task.board.service

import com.task.board.domain.Post
import com.task.board.exception.PostNotFoundException
import com.task.board.repository.LikeRepository
import com.task.board.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@SpringBootTest
class LikeServiceTest(
    private val likeService: LikeService,
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    given("좋아요 생성 시"){
        val saved = postRepository.save(Post("harris", "title", "content"))
        When("입력이 정상적으로 들어오면"){
            val likeId = likeService.createLike(saved.id, "harris")
            then("좋아요가 정상적으로 생성됨"){
               val like = likeRepository.findByIdOrNull(likeId)
               like shouldNotBe null
                like?.createdBy shouldBe "harris"
            }
        }
        When("게시글이 존재 하지 않을 시"){
            then("존재하지 않는 게시글 예외 발생"){
                shouldThrow<PostNotFoundException> { likeService.createLike(9999L, "harris") }
            }
        }
    }
})
