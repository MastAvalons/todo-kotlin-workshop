package com.epam.todokotlinworkshop.ui.taskdetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.epam.todokotlinworkshop.R
import com.epam.todokotlinworkshop.data.DataSource
import com.epam.todokotlinworkshop.data.Task
import com.epam.todokotlinworkshop.ui.DateUtils
import com.epam.todokotlinworkshop.ui.MY_NAME
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.util.*


class AddTaskFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private var selectedDate: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
                .also { initToolbar() }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDueDate.setOnClickListener(this)
        tvDueTime.setOnClickListener(this)
        imgDate.setOnClickListener(this)
        imgTime.setOnClickListener(this)
        btnSave.setOnClickListener(this)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = "Add new task"
            setDisplayHomeAsUpEnabled(true);
            setDisplayShowHomeEnabled(true);
        }
    }

    private fun showDateDialog() {
        selectedDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        val datePickerDialog = DatePickerDialog(
                context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun showTimeDialog() {
        selectedDate = Date()
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
                context, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
        timePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        selectedDate = calendar.time
        tvDueDate.text = DateUtils.getDate(selectedDate!!)
        tvDueTime.text = DateUtils.getTime(selectedDate!!)
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        selectedDate = calendar.time
        tvDueTime.text = DateUtils.getTime(selectedDate!!)
    }

    private fun saveTask() {
        if(etTitle.text.isEmpty()) Toast.makeText(context, "Title should not be empty", Toast.LENGTH_LONG).show()
        DataSource.addTask(
                Task(
                        etTitle.text.toString(),
                        etDescription.text.toString(),
                        MY_NAME,
                        selectedDate,
                        cbCompleted.isChecked
                )
        )
        activity?.onBackPressed()
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.tvDueDate -> showDateDialog()
            R.id.tvDueTime -> showTimeDialog()
            R.id.btnSave -> saveTask()
        }
    }

    companion object {

        fun newInstance(): AddTaskFragment {
            return AddTaskFragment()
        }
    }
}
