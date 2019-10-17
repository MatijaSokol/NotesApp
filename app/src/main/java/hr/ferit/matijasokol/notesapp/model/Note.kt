package hr.ferit.matijasokol.notesapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Notes")
@Parcelize
data class Note(
    val title: String,
    val description: String,
    val priority: Int,

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : Parcelable {

    //needed for diffUtil comparing
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass){
            return false
        }

        other as Note

        if (title != other.title || description != other.description || priority != other.priority){
            return false
        }

        return true
    }
}