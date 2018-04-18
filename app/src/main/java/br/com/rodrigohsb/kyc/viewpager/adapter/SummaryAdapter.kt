package br.com.rodrigohsb.kyc.viewpager.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigohsb.kyc.viewpager.Content
import br.com.rodrigohsb.kyc.viewpager.R
import kotlinx.android.synthetic.main.summary_row.view.*

/**
 * @rodrigohsb
 */
class SummaryAdapter (private val summary: List<Content>) : RecyclerView.Adapter<SummaryAdapter.ViewHolder>(){

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.summary_row, parent, false))
    }

    override fun getItemCount() = summary.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val content = summary[position]

        holder.itemView.question_id.text = content.question.id
        holder.itemView.question_text.text = content.question.text

        holder.itemView.answer_id.text = content.answer.id
        holder.itemView.answer_text.text = content.answer.text
    }
}