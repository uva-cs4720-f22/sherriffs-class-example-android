package edu.virginia.cs.cs4720.exampleapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

const val MESSAGE_TYPE = "edu.virginia.cs.MESSAGE"
const val RETURN_CODE = 1000

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        mainTextEdit.setText(sharedPref.getString(MESSAGE_TYPE, "NONE FOUND"))

    }

    val openSecondActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {   result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val returnedInfo = result.data?.getStringExtra(MESSAGE_TYPE)
            if(returnedInfo != null) {
                Log.i("SherriffsApp", returnedInfo)
                val toast = Toast.makeText(applicationContext, returnedInfo, Toast.LENGTH_LONG)
                toast.show()
            }


        }
        else {
            Log.e("SherriffsApp", result.resultCode.toString())
        }
    }

    fun updateMainTextView(view: View) {
        val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        mainTextEdit.setText("Hi CS 4720!")
    }

    fun sendText(view: View) {

        val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        val message = mainTextEdit.text.toString()
        val intent = Intent(this, GetMessageActivity::class.java).apply {
            putExtra(MESSAGE_TYPE, message)
        }
        Log.i("Sherriffs App", "Pressed Button: " + message)
        openSecondActivity.launch(intent)

    }


    override fun onStop() {
        super.onStop()

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
            val message = mainTextEdit.text.toString()
            putString(MESSAGE_TYPE, message)
            apply()
            Log.i("Sherriffs App", "onStop: " + message)
        }
    }

}