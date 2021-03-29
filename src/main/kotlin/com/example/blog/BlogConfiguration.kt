package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {
    @Bean
    fun databaseInitializer(userRepository: UserRepository, articleRepository: ArticleRepository) =
            ApplicationRunner {
                val saro = userRepository.save(
                        User("saro", "Sarawanan", "Mahalingam")
                )
                articleRepository.save(Article(
                        title = "Title 1",
                        headline = "Headline 1",
                        content = "Content 1",
                        author = saro
                ))
                articleRepository.save(Article(
                        title = "Title 2",
                        headline = "Headline 2",
                        content = "Content 2",
                        author = saro
                ))
            }
}