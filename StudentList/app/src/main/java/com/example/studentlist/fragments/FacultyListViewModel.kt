package com.example.studentlist.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.studentlist.data.Faculty
import com.example.studentlist.data.ListOfFaculty
import com.example.studentlist.MyConsts.TAG
import com.example.studentlist.repository.AppRepository

class FacultyListViewModel : ViewModel() {
    var facultyList: MutableLiveData<ListOfFaculty> = MutableLiveData()
    private var facultyCurrent : Faculty = Faculty()
    private var _faculty : Faculty? = null
    val faculty
        get() = _faculty

    private var observer = Observer<ListOfFaculty?>{
            newList ->
        newList?.let{
            Log.d(TAG, "Получен список ListOfFaculty от Repository")
            facultyList.postValue(newList)
        }
    }

    init{
        AppRepository.getInstance().faculty.observeForever{
            facultyCurrent=it
            Log.d(TAG, "Получили Faculty от Repository")
        }
        AppRepository.getInstance().listOfFaculty.observeForever(observer)
    }

    fun setFaculty(faculty: Faculty){
        AppRepository.getInstance().setCurrentFaculty(faculty)
    }

    fun getPosition(): Int =
        AppRepository.getInstance().getFacultyPosition()

    fun deleteFaculty(){
        if(faculty!=null)
            AppRepository.getInstance().updateFaculty(_faculty!!)
    }
    fun appendFaculty(facultyName : String){
        val faculty = Faculty()
        faculty.name=facultyName
        AppRepository.getInstance().updateFaculty(faculty)
    }
     fun updateFaculty(facultyName : String){
         if(_faculty!=null){
             _faculty!!.name=facultyName
             AppRepository.getInstance().updateFaculty(_faculty!!)
         }
     }


}