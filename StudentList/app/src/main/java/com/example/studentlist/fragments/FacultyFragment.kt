package com.example.studentlist.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentlist.MainActivity
import com.example.studentlist.R
import com.example.studentlist.data.Faculty
import com.example.studentlist.databinding.FragmentFacultyListBinding

class FacultyFragment : Fragment(), MainActivity.Edit {

    companion object {
        private var INSTANCE : FacultyFragment? = null
        fun getInstance() : FacultyFragment{
            if(INSTANCE == null) INSTANCE = FacultyFragment()
            return INSTANCE ?: throw Exception("FacultyFragment не создан")
        }
    }

    private lateinit var viewModel: FacultyListViewModel
    private var _binding : FragmentFacultyListBinding? = null

    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFacultyListBinding.inflate(inflater, container, false)
        binding.rvFaculty.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyListViewModel::class.java)
        viewModel.facultyList.observe(viewLifecycleOwner){
            binding.rvFaculty.adapter=FacultyListAdapter(it.items)
        }
    }

    private inner class FacultyListAdapter(private val items: List<Faculty>)
        : RecyclerView.Adapter<FacultyListAdapter.ItemHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyListAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_faculty_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: FacultyListAdapter.ItemHolder, position: Int) {
            holder.bind(items[position])
        }

        private inner class ItemHolder(view: View)
            :RecyclerView.ViewHolder(view){
            private lateinit var  faculty: Faculty

            fun bind(faculty: Faculty){
                this.faculty = faculty
                itemView.findViewById<TextView>(R.id.tvFaculty).text = faculty.name
            }

        }

    }

    override fun append() {
        editFaculty()
    }

    override fun update() {
        editFaculty(viewModel.faculty?.name?: "")
    }

    override fun delete() {
       deleteDialog()
    }

    private fun deleteDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить факультет ${viewModel.faculty?.name ?: ""}")
            .setPositiveButton("Да"){ _, _ ->
                viewModel.deleteFaculty()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }

    @SuppressLint("MissingInflatedId")
    private fun editFaculty(facultyName : String=""){
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_string, null)
        val messageText = mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString = mDialogView.findViewById<EditText>(R.id.etString)
        inputString.setText(facultyName)
        messageText.text = "Укажите наименование факультета"

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("ИЗМЕНЕНИЕ ДАННЫХ")
            .setView(mDialogView)
            .setPositiveButton("подтверждаю"){ _, _ ->
                if(inputString.text.isNotBlank()){
                    if(facultyName.isBlank())
                        viewModel.appendFaculty(inputString.text.toString())
                    else
                        viewModel.updateFaculty(inputString.text.toString())
                }
            }
            .setNegativeButton("отмена", null)
            .setCancelable(true)
            .create()
            .show()
    }


}