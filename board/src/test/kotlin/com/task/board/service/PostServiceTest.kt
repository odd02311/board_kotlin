package com.task.board.service

import com.task.board.domain.Post
import com.task.board.dto.PostSearchRequest
import com.task.board.exception.PostNotDeletableException
import com.task.board.exception.PostNotFoundException
import com.task.board.exception.PostNotUpdatableException
import com.task.board.repository.PostRepository
import com.task.board.service.dto.PostCreateRequestDto
import com.task.board.service.dto.PostSearchRequestDto
import com.task.board.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    beforeSpec {
        postRepository.saveAll(
            listOf(
                Post(title= "title1", content= "content1", createdBy = "harris1"),
                Post(title= "title12", content= "content2", createdBy = "harris1"),
                Post(title= "title13", content= "content3", createdBy = "harris1"),
                Post(title= "title14", content= "content4", createdBy = "harris1"),
                Post(title= "title15", content= "content5", createdBy = "harris1"),
                Post(title= "title6", content= "content6", createdBy = "harris2"),
                Post(title= "title7", content= "content7", createdBy = "harris2"),
                Post(title= "title8", content= "content8", createdBy = "harris2"),
                Post(title= "title9", content= "content9", createdBy = "harris2"),
                Post(title= "title10", content= "content10", createdBy = "harris2"),
            )
        )
    }
    given("게시글 생성 시") {
        When("게시글 인풋이 정상적으로 들어오면") {
            val postId = postService.createPost(
                PostCreateRequestDto(
                    title = "제목",
                    content = "내용",
                    createdBy = "harris"
                )
            )
            then("게시글이 정상적으로 생성됨을 확인함") {
                postId shouldBeGreaterThan 0L
                val post = postRepository.findByIdOrNull(postId)
                post shouldNotBe null
                post?.title shouldBe "제목"
                post?.content shouldBe "내용"
                post?.createdBy shouldBe "harris"
            }
        }
    }
    given("게시글 수정 시") {
        val saved = postRepository.save(Post(title = "title", content = "content", createdBy = "harris"))
        When("정상 수정 시") {
            val updatedId = postService.updatePost(
                saved.id,
                PostUpdateRequestDto(
                    title = "updated title",
                    content = "updated content",
                    updatedBy = "harris"
                )
            )
            then("게시글이 정상적으로 수정됨을 확인함") {
                saved.id shouldBe updatedId
                val updated = postRepository.findByIdOrNull(updatedId)
                updated shouldNotBe null
                updated?.title shouldBe "updated title"
                updated?.content shouldBe "updated content"
            }
        }
        When("게시글이 없을 시") {
            then("게시글을 찾을 수 없다 예외 발생") {
                shouldThrow<PostNotFoundException> {
                    postService.updatePost(
                        9999L,
                        PostUpdateRequestDto(
                            title = "update title",
                            content = "update content",
                            updatedBy = "update harris"
                        )
                    )
                }
            }
        }
        When("작성자가 동일하지 않으면") {
            then("수정할 수 없는 게시물 입니다 예외 발생") {
                shouldThrow<PostNotUpdatableException> {
                    postService.updatePost(
                        1L,
                        PostUpdateRequestDto(
                            title = "update title",
                            content = "update content",
                            updatedBy = "update harris"
                        )
                    )
                }
            }
        }
    }
    given("게시글 삭제 시") {
        val saved = postRepository.save(Post(title = "title", content = "content", createdBy = "harris"))
        When("정상 삭제 시") {
            val postId = postService.deletePost(saved.id, "harris")
            then("게시글이 정상적으로 삭제됨") {
                postId shouldBe saved.id
                postRepository.findByIdOrNull(postId) shouldBe null
            }
        }
        When("작성자가 동일하지 않으면") {
            val saved2 = postRepository.save(Post(title = "title", content = "content", createdBy = "harris"))
            then("삭제할 수 없는 게시물 입니다 예외가 발생한다.") {
                shouldThrow<PostNotDeletableException> { postService.deletePost(saved2.id, "harris2") }
            }
        }
    }
    given("게시글 상세조회 시") {
        val saved = postRepository.save(Post(title = "title", content = "content", createdBy = "harris"))
        When("정상 조회 시") {
            val post = postService.getPost(saved.id)
            then("게시글의 내용이 정상적으로 반환됨 확인") {
                post.id shouldBe saved.id
                post.title shouldBe "title"
                post.content shouldBe "content"
                post.createdBy shouldBe "harris"
            }
        }
        When("게시글이 없을 때") {
            then("게시글을 찾을 수 없다 예외 발생") {
                shouldThrow<PostNotFoundException> { postService.getPost(9999L) }
            }
        }
    }
    given("게시글 목록조회 시"){
        When("정상 조회시"){
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto())
            then("게시글 페이지가 반환된다."){
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content[0].title shouldContain "title"
                postPage.content[0].createdBy shouldContain "harris"
            }
        }
        When("title로 검색"){
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(title = "title1"))
            then("타이틀에 해당하는 게시글이 반환된다"){
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content[0].title shouldContain "title1"
                postPage.content[0].createdBy shouldContain "harris"
            }
        }
        When("createdBy로 검색"){
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(createdBy = "harris1"))
            then("작성자에 해당하는 게시글이 반환."){
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content[0].title shouldContain "title"
                postPage.content[0].createdBy shouldBe "harris1"
            }
        }
    }


})
