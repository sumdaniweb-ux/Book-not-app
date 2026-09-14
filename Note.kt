package com.example.noteapp

data class Note(
    var title: String = "",
    var description: String = "",
    var priority: Int = 1
) {
    var id: Int = -1
}