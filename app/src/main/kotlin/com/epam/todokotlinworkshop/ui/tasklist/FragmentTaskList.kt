package com.epam.todokotlinworkshop.ui.tasklist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epam.todokotlinworkshop.R
import com.epam.todokotlinworkshop.data.Task
import com.epam.todokotlinworkshop.ui.showFragment
import com.epam.todokotlinworkshop.ui.taskdetails.AddTaskFragment
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_task_list.*


class FragmentTaskList : Fragment() {

    private lateinit var taskListAdapter: TaskListAdapter

    private var disposable: ListenerRegistration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
                .apply {
                    taskListAdapter = TaskListAdapter()
                    with(findViewById<RecyclerView>(R.id.rvTasks)) {
                        adapter = taskListAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                }


//        val root = inflater.inflate(R.layout.fragment_task_list, container, false)
//        root.apply {
//            taskListAdapter = TaskListAdapter()
//            with(findViewById<RecyclerView>(R.id.rvTasks)) {
//                adapter = taskListAdapter
//                layoutManager = LinearLayoutManager(context)
//            }
//        }
//
//
//        disposable = DataSource.observerTasks(::displayTasks)
//
//        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddTask.setOnClickListener {
            showFragment(AddTaskFragment.newInstance())
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Task manager"
            setDisplayHomeAsUpEnabled(false);
            setDisplayShowHomeEnabled(false);
        }
    }

    private fun displayTasks(tasks: List<Task>) {
        val items = tasks
                .sortedWith(compareBy({ it.authorName }, { it.dueDate }))
                .map { TaskItem(it) }
                .toMutableList<Item>()
                .let { addHeaders(it) }
        taskListAdapter.items = items

    }

    private fun addHeaders(taskItemList: MutableList<Item>): List<Item> {
        fun Item.toTask(): TaskItem = this as TaskItem

        val authors = taskItemList
                .map { it.toTask().task.authorName }.toSet()


        authors.forEach { author ->
            val firstIndex = taskItemList.indexOfFirst {
                it is TaskItem && it.toTask().task.authorName == author
            }
            taskItemList.add(firstIndex, Header(author))
        }
        return taskItemList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.remove()
    }

    companion object {
        fun newInstance(): FragmentTaskList {
            return FragmentTaskList()
        }
    }
}