package com.example.noteapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.noteapp.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.noteapp.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.example.noteapp.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.example.noteapp.EXTRA_PRIORITY"
    }

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var seekBarPriority: SeekBar
    private lateinit var textViewPriority: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        seekBarPriority = findViewById(R.id.seekBarPriority)
        textViewPriority = findViewById(R.id.textViewPriority)

        seekBarPriority.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewPriority.text = "Priority: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            seekBarPriority.progress = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Note"
        }

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val priority = seekBarPriority.progress

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_DESCRIPTION, description)
            putExtra(EXTRA_PRIORITY, priority)
        }

        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id != -1) {
            data.putExtra(EXTRA_ID, id)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}