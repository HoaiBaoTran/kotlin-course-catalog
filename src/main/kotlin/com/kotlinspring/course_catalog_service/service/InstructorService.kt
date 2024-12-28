package com.kotlinspring.course_catalog_service.service

import com.kotlinspring.course_catalog_service.dto.InstructorDTO
import com.kotlinspring.course_catalog_service.entity.Instructor
import com.kotlinspring.course_catalog_service.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(
    val instructorRepository: InstructorRepository
) {

    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {

        val instructor = instructorDTO.let {
            Instructor(it.id, it.name)
        }

        instructorRepository.save(instructor)

        return instructor.let {
            InstructorDTO(it.id, it.name)
        }

    }

    fun findByInstructorId(id: Int): Optional<Instructor> {
        return instructorRepository.findById(id)
    }

}
