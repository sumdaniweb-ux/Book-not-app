package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_ADD = 1
        private const val REQUEST_CODE_EDIT = 2
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var fabAddNote: FloatingActionButton

    private val notes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        noteAdapter = NoteAdapter(notes)
        recyclerView.adapter = noteAdapter

        fabAddNote = findViewById(R.id.fabAddNote)
        fabAddNote.setOnClickListener {
            addEditNote()
        }

        noteAdapter.setOnItemClickListener { note ->
            addEditNote(note)
        }
    }

    private fun addEditNote(note: Note? = null) {
        val intent = Intent(this, AddEditNoteActivity::class.java)

        if (note != null) {
            intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
            intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
            intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
            intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
        }

        if (note == null) {
            startActivityForResult(intent, REQUEST_CODE_ADD)
        } else {
            startActivityForResult(intent, REQUEST_CODE_EDIT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            val title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE) ?: ""
            val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION) ?: ""
            val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            val note = Note(title, description, priority)
            if (id != -1) {
                note.id = id
                updateNote(note)
            } else {
                insertNote(note)
            }
        }
    }

    private fun insertNote(note: Note) {
        notes.add(note)
        noteAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
    }

    private fun updateNote(note: Note) {
        val position = notes.indexOfFirst { it.id == note.id }
        if (position != -1) {
            notes[position] = note
            noteAdapter.notifyItemChanged(position)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            notes.clear()
            noteAdapter.notifyDataSetChanged()
            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}