package com.example.blog

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val userRepository: UserRepository,
        val articleRepository: ArticleRepository) {

    @Test
    fun `When findByIdOrNull then return Article`() {
        val saro = User("saro", "Sarawanan", "Mahalingam")
        entityManager.persist(saro)
        val article = Article("Title 1", "Headline 1", "Content 1", saro)
        entityManager.persist(article)
        entityManager.flush()
        val found = articleRepository.findByIdOrNull(article.id!!)
        assertEquals(found, article)
    }

    @Test
    fun `When findByLogin then return User`() {
        val user = User("saro", "Sarawanan", "Mahalingam")
        entityManager.persist(user)
        entityManager.flush()
        val found = userRepository.findByLogin(user.login)
        assertEquals(found, user)
    }
}