package com.kotlinspring.course_catalog_service.repository

import com.kotlinspring.course_catalog_service.dto.InstructorDTO
import com.kotlinspring.course_catalog_service.entity.Instructor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InstructorRepository : CrudRepository<Instructor, Int>
