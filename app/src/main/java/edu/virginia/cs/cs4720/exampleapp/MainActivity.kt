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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Shared Preferences example - this shows how to store simple key/value
        // pairs in an app.
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        mainTextEdit.setText(sharedPref.getString(MESSAGE_TYPE, "NONE FOUND"))

    }

    // Example of opening an activity so it can return some information back to this app
    // In this example, the data in a form field is returned and then popped with a toast
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

    // Function not currently being called, but is a very simple example of a function
    // that changes the text inside an EditText
    fun updateMainTextView(view: View) {
        val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        mainTextEdit.setText("Hi CS 4720!")
    }

    // Function that is called by the button press that opens another activity
    // using an Intent and gets a return (see the openSecondActivity definition above
    fun sendText(view: View) {
        val mainTextEdit = findViewById<EditText>(R.id.editTextTextPersonName)
        val message = mainTextEdit.text.toString()
        val intent = Intent(this, GetMessageActivity::class.java).apply {
            putExtra(MESSAGE_TYPE, message)
        }
        Log.i("Sherriffs App", "Pressed Button: " + message)
        openSecondActivity.launch(intent)

    }

    // Function that is overridden for when the activity stops
    // this is called whenever the activity fully leaves the visible screen
    // In this case, this will also save the current value of the EditText
    // to shared preferences.
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