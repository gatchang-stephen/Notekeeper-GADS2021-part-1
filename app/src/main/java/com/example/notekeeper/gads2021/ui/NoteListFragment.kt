package com.example.notekeeper.gads2021.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notekeeper.gads2021.R
import com.example.notekeeper.gads2021.data.DataManager
import com.example.notekeeper.gads2021.databinding.FragmentNoteListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_NoteListFragment_to_NoteFragment)
        }

        binding.listNotes.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            DataManager.notes
        )
        binding.listNotes.setOnItemClickListener { _, _, p3, _ ->
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(p3)
            activity?.findNavController(R.id.nav_host_fragment_content_main)?.navigate(action)
        }
    }
    override fun onResume() {
        super.onResume()
        binding.listNotes.adapter.areAllItemsEnabled()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}