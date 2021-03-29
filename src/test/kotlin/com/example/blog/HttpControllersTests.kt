package com.example.blog

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var userRepository: UserRepository

    @MockkBean
    private lateinit var articleRepository: ArticleRepository

    @Test
    fun `List articles`() {
        val user = User("saro", "Sarawanan", "Mahalingam")
        val article1 = Article("Title 1", "Headline 1", "Content 1", user)
        val article2 = Article("Title 2", "Headline 2", "Content 2", user)
        every { articleRepository.findAllByOrderByAddedAtDesc() } returns listOf(article1, article2)
        mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].author.login").value(user.login))
                .andExpect(jsonPath("\$.[0].slug").value(article1.slug))
                .andExpect(jsonPath("\$.[1].author.login").value(user.login))
                .andExpect(jsonPath("\$.[1].slug").value(article2.slug))
    }

    @Test
    fun `List users`() {
        val saro = User("saro", "Sarawanan", "Mahalingam")
        val mammu = User("mammu", "Krithika", "Sarawanan")
        every { userRepository.findAll() } returns listOf(saro, mammu)
        mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.[0].login").value(saro.login))
                .andExpect(jsonPath("\$.[1].login").value(mammu.login))

    }
}
