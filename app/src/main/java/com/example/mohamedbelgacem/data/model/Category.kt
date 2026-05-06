package com.example.mohamedbelgacem.data.model

/**
 * Quiz Category Data Model
 *
 * Represents a quiz category (e.g., History, Geography, Culture).
 * Contains metadata about the category.
 *
 * @param id Unique category identifier
 * @param name Display name
 * @param description Category description
 * @param questionCount Total questions in this category
 * @param icon Category icon resource (to be implemented)
 */
data class Category(
    val id: String,
    val name: String,
    val description: String,
    val questionCount: Int,
    val icon: String = ""
)
