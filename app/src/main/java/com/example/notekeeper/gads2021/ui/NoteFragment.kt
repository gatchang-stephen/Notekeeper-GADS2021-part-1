package com.example.notekeeper.gads2021.ui

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.notekeeper.gads2021.R
import com.example.notekeeper.gads2021.data.*
import com.example.notekeeper.gads2021.databinding.FragmentNoteBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NoteFragment : Fragment() {
    private var notePosition = POSITION_NOT_SET

    private var _binding: FragmentNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterCourses = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        // Specify the layout to use when the list of choices appears
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET)
            ?: arguments?.let { NoteFragmentArgs.fromBundle(it).myArg }!!

        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        binding.noteTitle.setText(note.title)
        binding.noteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        binding.spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                if (notePosition < DataManager.notes.lastIndex)
                    moveNext()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (notePosition >= DataManager.notes.lastIndex) {
            val itemMenu = menu.findItem(R.id.action_next)
            if (itemMenu != null) {
                itemMenu.icon =
                    AppCompatResources.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.ic_block_24
                    )
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = binding.noteTitle.text.toString()
        note.text = binding.noteText.text.toString()
        note.course = binding.spinnerCourses.selectedItem as CourseInfo?
    }


    private fun moveNext() {
        ++notePosition
        displayNote()
        requireActivity().invalidateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}