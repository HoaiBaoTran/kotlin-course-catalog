package com.kotlinspring.course_catalog_service.controller

import com.kotlinspring.course_catalog_service.dto.CourseDTO
import com.kotlinspring.course_catalog_service.entity.Course
import com.kotlinspring.course_catalog_service.service.CourseService
import com.kotlinspring.course_catalog_service.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(
            null,
            "Build Restful APIs using SPringBoot and Kotlin",
            "Luffy"
        )

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun addCourse_validation() {
        val courseDTO = CourseDTO(
            null,
            "",
            ""
        )

        every { courseServiceMock.addCourse(any()) } returns courseDTO(id = 1)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("courseDTO.category must not be blank, courseDTO.name must not be blank", response)
    }

    @Test
    fun addCourse_runtimeException() {

        val courseDTO = CourseDTO(
            null,
            "Build Restful APIs using SPringBoot and Kotlin",
            "Luffy"
        )

        val errorMessage = "Unexpected Error occurred"
        every { courseServiceMock.addCourse(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }

    @Test
    fun retrieveAllCourses() {

        every {
            courseServiceMock.retrieveAllCourses(any())
        }.returns(
            listOf(
                courseDTO(id = 1),
                courseDTO(id = 2, name = "Build Reactive Microservices using Spring WebFlux/SpringBoot"),
                courseDTO(id = 3, name = "Wiremock for Java Developers"),
            )
        )

        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs: $courseDTOs")
        assertEquals(3, courseDTOs!!.size)
    }

    @Test
    fun updateCourse() {
        // existingCourse
        val course = Course(
            null,
            "Build RestFul APis using SpringBoot and Kotlin",
            "Development"
        )

        every {
            courseServiceMock.updateCourse(any(), any())
        } returns courseDTO(id = 100, name = "Build RestFul APis using SpringBoot and Kotlin10")
//                CourseDTO(
//                    100,
//                    "Build RestFul APis using SpringBoot and Kotlin",
//                    "Development"
//                )

        // courseId
        // updated CourseDTO
        val courseDTO = CourseDTO(
            null,
            "Build RestFul APis using SpringBoot and Kotlin10",
            "Design"
        )

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Build RestFul APis using SpringBoot and Kotlin10", updatedCourse!!.name)
    }

    @Test
    fun deleteCourse() {

        every {
            courseServiceMock.deleteCourse(any())
        } just runs

        val deleteCourse = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent

    }
}