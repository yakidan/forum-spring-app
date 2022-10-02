package com.yakidan.spring.test_project.forum.entity

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name = "comments")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "text")
    val text: String,

    @ManyToOne
    @JoinColumn(name = "topic_id")
    val topic: Topic,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    ) {
}