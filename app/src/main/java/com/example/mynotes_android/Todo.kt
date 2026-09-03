package com.example.mynotes_android

data class Todo(
    val id: Int,
    val title: String,
    val isCompleted: Boolean = false
)