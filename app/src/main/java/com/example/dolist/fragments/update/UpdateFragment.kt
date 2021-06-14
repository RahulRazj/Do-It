package com.example.dolist.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dolist.R
import com.example.dolist.data.models.ToDoData
import com.example.dolist.data.viewmodel.ToDoViewModel
import com.example.dolist.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data Binding
        val view =  inflater.inflate(R.layout.fragment_update, container, false)


        setHasOptionsMenu(true)

        view.current_etTitle.setText(args.currentItem.title)
        view.current_etTitle.setTextColor(ContextCompat.getColor(requireContext(),R.color.title_color))
        view.current_etDescription.setText(args.currentItem.description)
        view.current_etDescription.setTextColor(ContextCompat.getColor(requireContext(),R.color.desc_color))
        view.current_spinnerPriorities.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.current_spinnerPriorities.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemDelete()
        }

        return super.onOptionsItemSelected(item)
    }



    private fun updateItem() {
        val title = current_etTitle.text.toString()
        val description = current_etDescription.text.toString()
        val priority = mSharedViewModel.parsePriority(current_spinnerPriorities.selectedItem.toString())

        val validate = mSharedViewModel.verifyData(title, description)
        if(validate) {
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                description,
                priority
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(),"Updated Successfully!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
        } else {
            Toast.makeText(requireContext(),"Please fill out Title and/or Description",Toast.LENGTH_LONG).show()
        }
    }

    // Show Alert Dialog to confirm Delete
    private fun confirmItemDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_, ->
            mToDoViewModel.deleteData(args.currentItem)
            Toast.makeText(requireContext(),"Task Deleted: ${current_etTitle.text}.",Toast.LENGTH_SHORT).show()
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToListFragment())
        }
        builder.setNegativeButton("No") {_,_,->

        }
        builder.setTitle("Delete '${current_etTitle.text}'")
        builder.setMessage("Are you sure you want to remove '${current_etTitle.text}'?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }



}