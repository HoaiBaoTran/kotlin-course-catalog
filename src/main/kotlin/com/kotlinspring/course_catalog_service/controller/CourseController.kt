package com.kotlinspring.course_catalog_service.controller

import com.kotlinspring.course_catalog_service.dto.CourseDTO
import com.kotlinspring.course_catalog_service.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(
    val courseService: CourseService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Validated courseDTO: CourseDTO): CourseDTO {
        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required = false) courseName: String?): List<CourseDTO> =
        courseService.retrieveAllCourses(courseName)

    @PutMapping("/{course_id}")
    fun updateCourse(
        @PathVariable("course_id") courseId: Int,
        @RequestBody courseDTO: CourseDTO
    ): CourseDTO = courseService.updateCourse(courseId, courseDTO)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId: Int) = courseService.deleteCourse(courseId)
}