import android.support.annotation.LayoutRes
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epam.todokotlinworkshop.R
import com.epam.todokotlinworkshop.data.Task


class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    var items: List<Item> = ArrayList()
        set(value) {
            (field as MutableList<Item>).apply {
                clear()
                addAll(value)
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, layoutRes: Int) =
            LayoutInflater
                    .from(parent.context)
                    .inflate(layoutRes, parent, false)
                    .let(::ViewHolder)


    override fun getItemCount() = if (items.isNotEmpty()) items.size else 1


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.isEmpty()) return

        items[position].also { item ->
            when (item) {
                is Header -> holder.title.text = item.title
                is TaskItem -> holder.apply {
                    title.text = item.name
                    dueDate.text = item.dueDate
                    checkbox.isChecked = item.checked
                }
            }
        }

    }


    override fun getItemViewType(position: Int) =
            if (items.isNotEmpty()) items[position].layoutRes
            else Empty.layoutRes


    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val title = root.findViewById<TextView>(R.id.tvTitle)
        val dueDate = root.findViewById<TextView>(R.id.tvDueDate)
        val checkbox = root.findViewById<AppCompatCheckBox>(R.id.cbChecked)
    }

}

sealed class Item
data class TaskItem(val task: Task) : Item() {
    val name: String
        get() = task.name
    val dueDate: String
        get() = task.dueDate?.toString() ?: "00:00"
    val checked: Boolean
        get() = task.completed
}
data class Header(val title: String) : Item()
object Empty : Item()

@get:LayoutRes
val Item.layoutRes
    get() = when (this) {
        is TaskItem -> R.layout.item_list_task
        is Header -> R.layout.item_list_header
        is Empty -> R.layout.item_list_empty
    }
