package com.example.dolist.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.dolist.R
import com.example.dolist.data.models.ToDoData
import com.example.dolist.data.viewmodel.ToDoViewModel
import com.example.dolist.databinding.FragmentListBinding
import com.example.dolist.fragments.SharedViewModel
import com.example.dolist.fragments.list.adapter.ListAdapter
import com.example.dolist.hideKeyBoard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import java.text.FieldPosition


class ListFragment : Fragment(), SearchView.OnQueryTextListener {


    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data Binding
        _binding = FragmentListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        // Setup Recycler View
        setupRecyclerView()

        // Observing Live Data
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatbaseEmpty(data)
            adapter.setData(data)
        })


        setHasOptionsMenu(true)

        // Hide keyboard
        hideKeyBoard(requireActivity())

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInDownAnimator().apply {
            addDuration = 300

        }

        // Swipe to delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallBack = object: SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                // Delete Item
                mToDoViewModel.deleteData(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // Restore Deleted Item
                restoreDeletedItem(viewHolder.itemView,deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(view: View, deletedData: ToDoData) {
        val snackBar = Snackbar.make(
            view,
            "Deleted ${deletedData.title}",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            mToDoViewModel.insertData(deletedData)
        }
        snackBar.show()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.menu_delete_all -> confirmDeleteAll()
            R.id.menu_priority_high -> mToDoViewModel.sortByPriorityHigh.observe(this,
                Observer { adapter.setData(it) })
            R.id.menu_priority_low -> mToDoViewModel.sortByPriorityLow.observe(this,
                Observer { adapter.setData(it) })
            R.id.menu_about -> findNavController().navigate(ListFragmentDirections.actionListFragmentToAboutFragment())
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchDataBase(query)
        }
        return true
    }



    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchDataBase(query)
        }
        return true
    }

    private fun searchDataBase(query: String) {

        val searchQuery = "%$query%"
        mToDoViewModel.searchDataBase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(list)
            }

        })
    }

    private fun confirmDeleteAll() {


        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(),"All Task Deleted.", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_,_->

        }
        builder.setTitle("Delete All Tasks")
        builder.setMessage("Are you sure you want to remove all the Tasks?")
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }


}