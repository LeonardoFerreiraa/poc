package br.com.leonardoferreira.poc.domain.response

data class TodoResponse(
    val id: Long,
    val userId: Long,
    val title: String,
    val completed: Boolean
)
