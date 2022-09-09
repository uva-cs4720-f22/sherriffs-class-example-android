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

        // Takes the info passed into this activity by the Intent
        // and puts it in the TextView
        // The .apply command here is equivalent to
        // textview.setText(message)
        val message = intent.getStringExtra(MESSAGE_TYPE)
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }
        // The above is the same as this.  The apply just looks cooler (I guess)
        // textView.setText(message)
    }

    // Function that is called when you click the button
    // creates an Intent with the data we want to send back
    // uses setResult to show that the activity was a "good close" and attaches the Intent
    // then calls finish() to actually end the activity
    // We don't override onStop() here because we don't need to.  We want this action to take
    // place BEFORE the UI elements are destroyed.
    fun closeActivity(view: View) {
        val switch = findViewById<Switch>(R.id.switch1)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(MESSAGE_TYPE, "The switch is: " + switch.isChecked)
        }
        setResult(Activity.RESULT_OK,intent)
        finish()

    }
}