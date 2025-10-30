package at.willhaben.applicants_test_project.listview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.core.text.HtmlCompat.fromHtml
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import at.willhaben.applicants_test_project.OpenDetailsScreenCallback
import at.willhaben.applicants_test_project.R
import at.willhaben.applicants_test_project.databinding.ListviewItemBinding
import at.willhaben.applicants_test_project.models.Page

class ListViewAdapter(private val openDetailsScreenCallback: OpenDetailsScreenCallback?) :
    RecyclerView.Adapter<ListViewHolder>() {
    private var items: List<Page> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Page>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(layoutInflater.inflate(R.layout.listview_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position]) { page ->
            openDetailsScreenCallback?.openScreen(page.title ?: "")
        }
    }
}

class ListViewHolder(itemView: View) : ViewHolder(itemView) {
    private val binding = ListviewItemBinding.bind(itemView)
    fun bind(page: Page, onClickListener: (Page) -> Unit) {
        binding.root.setOnClickListener { onClickListener(page) }
        binding.listItemTitle.text = page.title
        binding.listItemBody.text = fromHtml(page.snippet ?: "", FROM_HTML_MODE_COMPACT)
    }
}

