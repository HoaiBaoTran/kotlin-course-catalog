package com.kotlinspring.course_catalog_service.entity

import jakarta.persistence.*

@Entity
@Table(name = "instructor")
data class Instructor(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,

    val name: String,

    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var courses: MutableList<Course> = mutableListOf(),
)