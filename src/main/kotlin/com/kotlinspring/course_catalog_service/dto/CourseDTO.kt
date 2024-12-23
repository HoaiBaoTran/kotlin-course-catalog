package com.kotlinspring.course_catalog_service.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(

    val id: Int?,

    @get:NotBlank(message = "courseDTO.name must not be blank")
    val name: String,

    @get:NotBlank(message = "courseDTO.category must not be blank")
    val category: String,
)