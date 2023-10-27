package com.example.studentlist.repository

import androidx.lifecycle.MutableLiveData
import com.example.studentlist.data.Faculty
import com.example.studentlist.data.ListOfFaculty
import java.lang.IllegalStateException

class AppRepository {
    companion object{
        private var INSTANCE: AppRepository? = null
        fun getInstance():AppRepository{
            if(INSTANCE == null){
                INSTANCE = AppRepository()
            }
            return INSTANCE?:
            throw IllegalStateException("Репозиторий не инициализирован")
        }
    }

    var listOfFaculty: MutableLiveData<ListOfFaculty?> = MutableLiveData()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()

    fun addFaculty(faculty: Faculty){
        var listTmp = listOfFaculty.value
        if(listTmp == null) listTmp = ListOfFaculty()
        listTmp.items.add(faculty)
        listOfFaculty.postValue(listTmp)
    }

    fun getFacultyPosition(faculty: Faculty): Int = listOfFaculty.value?.items?.indexOfFirst{
        it.id == faculty.id} ?: -1

    fun getFacultyPosition()=getFacultyPosition((faculty.value ?: Faculty()))

    fun setCurrentFaculty(position: Int){
        if (listOfFaculty.value == null || position<0 || (listOfFaculty.value?.items?.size!! <= position))
            return
        setCurrentFaculty(listOfFaculty.value?.items!![position])
    }

    fun setCurrentFaculty(faculty: Faculty){
        this.faculty.postValue(faculty)
    }

    fun updateFaculty(faculty: Faculty){
        val position = getFacultyPosition(faculty)
        if (position < 0) addFaculty(faculty)
        else{
            val listTmp = listOfFaculty.value!!
            listTmp.items[position]=faculty
            listOfFaculty.postValue(listTmp)
        }
    }
    fun deleteFaculty(faculty: Faculty){
        val listTmp = listOfFaculty.value!!
        if(listTmp.items.remove(faculty)){
            listOfFaculty.postValue(listTmp)
        }
        setCurrentFaculty(0)
    }

}