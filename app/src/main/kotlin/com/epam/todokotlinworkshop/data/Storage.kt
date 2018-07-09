package com.epam.todokotlinworkshop.data

import com.epam.todokotlinworkshop.ui.MY_NAME
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import com.google.android.gms.tasks.Task as FirestoreTask

data class Task(val name: String,
                val description: String,
                val authorName: String = MY_NAME,
                val dueDate: Date?,
                val completed: Boolean = false)

object DataSource {

    private val database: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private const val COLLECTION_NAME = "tasks"

    private const val FIELD_TASK_NAME = "name"
    private const val FIELD_DESCRIPTION = "description"
    private const val FIELD_DUE_DATE = "dueDate"
    private const val FIELD_AUTHOR_NAME = "authorName"

    private const val TASK_NAME_DEFAULT = "Task"
    private const val AUTHOR_NAME_DEFAULT = "Others"

    fun addTask(task: Task, callback: (isSuccessful: Boolean) -> Unit) {
        database.collection(COLLECTION_NAME)
                .add(task)
                .addOnCompleteListener {
                    callback(it.isSuccessful)
                }
    }

    fun getTasks(callback: (task: List<Task>) -> Unit) {
        database.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        it.result.documents
                                .map {
                                    Task(
                                            it.getString(FIELD_TASK_NAME) ?: TASK_NAME_DEFAULT,
                                            it.getString(FIELD_DESCRIPTION) ?: "",
                                            it.getString(FIELD_AUTHOR_NAME) ?: AUTHOR_NAME_DEFAULT,
                                            it.getDate(FIELD_DUE_DATE)
                                    )
                                }
                                .let { callback(it) }
                    }
                }
    }

    fun observeTasks(callback: (task: List<Task>) -> Unit): ListenerRegistration {
        return database.collection(COLLECTION_NAME)
                .addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                    if (e != null) return@EventListener

                    value
                            ?.map {
                                Task(
                                        it.getString(FIELD_TASK_NAME) ?: TASK_NAME_DEFAULT,
                                        it.getString(FIELD_DESCRIPTION) ?: "",
                                        it.getString(FIELD_AUTHOR_NAME) ?: AUTHOR_NAME_DEFAULT,
                                        it.getDate(FIELD_DUE_DATE)
                                )
                            }
                            ?.let { callback(it) }
                })
    }
}