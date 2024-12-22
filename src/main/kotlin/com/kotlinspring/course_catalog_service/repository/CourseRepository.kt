package com.kotlinspring.course_catalog_service.repository

import com.kotlinspring.course_catalog_service.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String): List<Course>

    @Query(value = "SELECT * FROM course WHERE name like %?1%", nativeQuery = true)
    fun findCoursesByName(courseName: String): List<Course>
}