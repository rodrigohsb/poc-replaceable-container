package br.com.rodrigohsb.kyc.viewpager.ui

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import br.com.rodrigohsb.kyc.viewpager.Content
import br.com.rodrigohsb.kyc.viewpager.R
import br.com.rodrigohsb.kyc.viewpager.State
import br.com.rodrigohsb.kyc.viewpager.domain.Summary
import br.com.rodrigohsb.kyc.viewpager.domain.question.Question
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleChoiceQuestion
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleInputQuestion
import br.com.rodrigohsb.kyc.viewpager.manager.ContentInterface
import br.com.rodrigohsb.kyc.viewpager.singleChoice.SingleChoiceFragment
import br.com.rodrigohsb.kyc.viewpager.singleInput.SingleInputFragment
import br.com.rodrigohsb.kyc.viewpager.summary.SummaryActivity
import br.com.rodrigohsb.kyc.viewpager.validate.Validator
import br.com.rodrigohsb.kyc.viewpager.viewModel.MyViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.comp_base.*
import kotlinx.android.synthetic.main.comp_loading.*

class MainActivity : AppCompatActivity() {

    lateinit var frags: ArrayList<Fragment>

    private var questions: List<Question>? = null

    private var indicator = 0

    private var content: MutableList<Content> = arrayListOf()

    private val viewModel by lazy{
        ViewModelProviders.of(this).get(MyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        close.setOnClickListener{
            finish()
        }

        prevButton.setOnClickListener{
            proceedToPreviousQuestion()
            content.removeAt(content.lastIndex)

        }
        nextButton.setOnClickListener{
            if(shouldShowNextQuestion()){
                savePair()
                proceedToNextQuestion()
            }
        }

        finishButton.setOnClickListener{
            savePair()
            SummaryActivity.start(this, Summary(content))
            finish()
        }
        
        viewModel
        .searchContent()
        .subscribe({ status: State ->
            when(status){
                is State.Error -> {}
                is State.Loading -> loading.visibility = View.VISIBLE
                is State.Sucess -> {
                    questions = status.questions
                    handleQuestions()
                }
            }
        },{
            t -> t.printStackTrace()
        })

    }

    private fun savePair() {
        val contentInterface = frags[indicator] as ContentInterface
        val question = contentInterface.getQuestion()
        val answer = contentInterface.getAnswer()

        content.add(Content(question, answer))
    }

    fun disableNextButton() {
        if(nextButton.visibility == View.VISIBLE){
            nextButton.isEnabled = false
            return
        }
        finishButton.isEnabled = false
    }

    fun enableNextButton() {

        if(nextButton.visibility == View.VISIBLE){
            nextButton.isEnabled = true
            return
        }
        finishButton.isEnabled = true
    }

    private fun shouldShowNextQuestion() = (frags[indicator] as Validator).validate()

    private fun proceedToPreviousQuestion() {
        handleButtons()
        popBackStack()
    }

    private fun proceedToNextQuestion(){
        ++indicator
        handleButtons()
        goToFragment(frags[indicator], false, "a")
    }

    private fun handleQuestions() {

        frags = ArrayList()

        for(question in questions!!){
            when(question){
                is SingleInputQuestion -> frags.add(SingleInputFragment.newInstance(question))
                is SingleChoiceQuestion -> frags.add(SingleChoiceFragment.newInstance(question))
            }
        }
        handleButtons()
        goToFragment(frags.first(), true, "a")
    }

    private fun popBackStack() {
        val count = supportFragmentManager.backStackEntryCount
        when (count) {
            in 2..Int.MAX_VALUE -> {
                indicator--
                handleButtons()
                supportFragmentManager.popBackStackImmediate()
            }
            1 -> {
                supportFragmentManager.popBackStackImmediate()
                indicator--
                finish()
            }
            else -> {
                finish()
            }
        }
    }

    private fun handleButtons(){

        if (isFirstQuestion()){
            prevButton.visibility = View.GONE
            finishButton.visibility = View.GONE

            nextButton.visibility = View.VISIBLE
            nextButton.isEnabled = false

            return
        }
        if(isLastPosition()){
            nextButton.visibility = View.GONE
            prevButton.visibility = View.VISIBLE

            finishButton.visibility = View.VISIBLE
            finishButton.isEnabled = false

            return
        }
        prevButton.visibility = View.VISIBLE
        finishButton.visibility = View.GONE

        nextButton.visibility = View.VISIBLE
        nextButton.isEnabled = false
    }

    private fun isLastPosition() = indicator == frags.size-1

    private fun isFirstQuestion() = indicator == 0

    private fun <T : Fragment> goToFragment(fragment: T, isRoot: Boolean, backStackTag: String) {
        if (isRoot) {
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
        }
        val hasFragment = supportFragmentManager.findFragmentById(R.id.frameLayout) != null
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
                        R.anim.enter_from_right, R.anim.exit_to_left)
                .let {
                    when (hasFragment) {
                        true -> it.replace(R.id.frameLayout, fragment)
                        false -> it.add(R.id.frameLayout, fragment)
                    }
                }
                .addToBackStack(backStackTag)
                .commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }

}
