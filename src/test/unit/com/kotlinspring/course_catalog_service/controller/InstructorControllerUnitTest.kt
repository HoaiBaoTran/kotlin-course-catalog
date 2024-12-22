package com.kotlinspring.course_catalog_service.controller

import com.kotlinspring.course_catalog_service.dto.InstructorDTO
import com.kotlinspring.course_catalog_service.service.InstructorService
import com.kotlinspring.course_catalog_service.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorServiceMock: InstructorService

    @Test
    fun addInstructor() {
        val instructorDTO = InstructorDTO(
            null,
            "Luffy"
        )

        every {
            instructorServiceMock.createInstructor(any())
        } returns InstructorDTO(id = 1, "Luffy")

        val savedInstructorDTO = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        assertTrue {
            savedInstructorDTO!!.id != null
        }
    }

    @Test
    fun createInstructor_Validation() {
        val instructorDTO = InstructorDTO(
            null,
            ""
        )

        every {
            instructorServiceMock.createInstructor(any())
        } returns InstructorDTO(id = 1, "Luffy")

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("instructorDTO.name must not be blank", response)
    }
}