package edu.virginia.cs.cs4720.exampleapp

import android.R.color
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GetMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_message)

        val message = intent.getStringExtra(MESSAGE_TYPE)
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
    }

    fun closeActivity(view: View) {
        val switch = findViewById<Switch>(R.id.switch1)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(MESSAGE_TYPE, "The switch is: " + switch.isChecked)
        }
        setResult(Activity.RESULT_OK,intent)
        finish()

    }
}