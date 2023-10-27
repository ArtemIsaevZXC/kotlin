package com.example.studentlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.studentlist.fragments.FacultyFragment

class MainActivity : AppCompatActivity() {
    interface Edit{
        fun append()
        fun update()
        fun delete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume(){
        super.onResume()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.miAppendFaculty -> {
                (FacultyFragment.getInstance() as Edit).append()
                true
            }
            R.id.miUpdateFaculty -> {
                (FacultyFragment.getInstance() as Edit).update()
                true
            }
            R.id.miDeleteFaculty -> {
                (FacultyFragment.getInstance() as Edit).delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}