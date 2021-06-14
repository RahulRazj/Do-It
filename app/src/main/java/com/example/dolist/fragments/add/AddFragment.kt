package com.example.dolist.fragments.add

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dolist.R
import com.example.dolist.data.models.ToDoData
import com.example.dolist.data.viewmodel.ToDoViewModel
import com.example.dolist.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {


    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        setHasOptionsMenu(true)

        view.spinnerPriorities.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_add) {
            insertDataToDb()

        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {

        val mTitle = etTitle.text.toString()
        val mDescription = etDescription.text.toString()
        val mPriority = spinnerPriorities.selectedItem.toString()

        val validation = mSharedViewModel.verifyData(mTitle, mDescription)

        if(validation) {

            // Insert Data
            val newData = ToDoData(
                0,
                mTitle,
                mDescription,
                mSharedViewModel.parsePriority(mPriority)
            )

            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully Added!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(AddFragmentDirections.actionAddFragmentToListFragment())
        } else {
            Toast.makeText(requireContext(),"Please fill Title and/or Description.",Toast.LENGTH_SHORT).show()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }
}