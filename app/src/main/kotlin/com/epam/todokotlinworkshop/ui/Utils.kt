package com.epam.todokotlinworkshop.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import com.epam.todokotlinworkshop.R
import com.epam.todokotlinworkshop.data.Task
import java.util.*

val MY_NAME: String get() = TODO()


fun FragmentActivity.showFragment(fragment: Fragment,
                                  containerId: Int = R.id.container,
                                  transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
    supportFragmentManager
            .beginTransaction()
            .setTransition(transition)
            .replace(containerId, fragment)
            .commit()
}

fun Fragment.showFragment(fragment: Fragment,
                          containerId: Int = R.id.container,
                          transition: Int = FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                          addToBackStack: Boolean = true) {
    fragmentManager
            ?.beginTransaction()
            ?.setTransition(transition)
            ?.replace(containerId, fragment)
            ?.addToBackStack(fragment::class.java.simpleName)
            ?.commit()
}

//fun mockTaskList() = listOf<Item>(
//        Header("Vasia Pupkin"),
//        TaskItem(Task("First Task", "", "Vasia Pupkin", null)),
//        TaskItem(Task("Second Task", "", "Vasia Pupkin", null)),
//        Divider,
//        Header("John Doe"),
//        TaskItem(Task("Third Task", "", "John Doe", Date())),
//        TaskItem(Task("Fourth Task", "", "John Doe", Date()))
//)

