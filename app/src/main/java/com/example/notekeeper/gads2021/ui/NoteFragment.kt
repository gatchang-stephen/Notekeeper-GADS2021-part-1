package com.example.notekeeper.gads2021.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.notekeeper.gads2021.data.DataManager
import com.example.notekeeper.gads2021.data.POSITION_NOT_SET
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

        notePosition = arguments?.let { NoteFragmentArgs.fromBundle(it).myArg }!!

        if (notePosition != POSITION_NOT_SET)
            displayNote()

    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        binding.noteTitle.setText(note.title)
        binding.noteText.setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        binding.spinnerCourses.setSelection(coursePosition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}