package hr.ferit.matijasokol.notesapp.common

import android.content.Context
import android.widget.Toast

fun Context.displayToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}