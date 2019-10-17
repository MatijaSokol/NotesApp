package hr.ferit.matijasokol.notesapp.database.repository

sealed class AsyncTaskCommands {
    object InsertNoteCommand : AsyncTaskCommands()
    object UpdateNoteCommand : AsyncTaskCommands()
    object DeleteNoteCommand : AsyncTaskCommands()
    object DeleteAllNotesCommand : AsyncTaskCommands()
}