package br.com.rodrigohsb.kyc.viewpager.summary

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import br.com.rodrigohsb.kyc.viewpager.R
import br.com.rodrigohsb.kyc.viewpager.adapter.SummaryAdapter
import br.com.rodrigohsb.kyc.viewpager.domain.Summary
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity() {


    companion object {
        fun start(activity: Activity, summary: Summary){

            val intent = Intent(activity, SummaryActivity::class.java)
            intent.putExtra("EXTRA_SUMMARY",summary)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val summary = intent.getParcelableExtra<Summary>("EXTRA_SUMMARY")

        with(summary_recyclerview){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SummaryActivity)
            addItemDecoration(DividerItemDecoration(this@SummaryActivity,DividerItemDecoration.VERTICAL))
            adapter = SummaryAdapter(summary.content)
        }
    }
}
