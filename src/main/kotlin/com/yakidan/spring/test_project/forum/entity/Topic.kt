package com.yakidan.spring.test_project.forum.entity

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name = "topics")
class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    ) {
    override fun toString(): String {
        return "Topic(id=$id, title='$title', description='$description', user=$user)"
    }
}