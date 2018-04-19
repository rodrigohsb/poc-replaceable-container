package br.com.rodrigohsb.kyc.viewpager.singleChoice

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import br.com.rodrigohsb.kyc.viewpager.R
import br.com.rodrigohsb.kyc.viewpager.domain.answer.DehydratedAnswer
import br.com.rodrigohsb.kyc.viewpager.domain.question.DehydratedQuestion
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleChoiceQuestion
import br.com.rodrigohsb.kyc.viewpager.manager.ContentInterface
import br.com.rodrigohsb.kyc.viewpager.ui.MainActivity
import kotlinx.android.synthetic.main.single_choice.*
import br.com.rodrigohsb.kyc.viewpager.validate.Validator
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @rodrigohsb
 */
class SingleChoiceFragment: Fragment(), Validator, ContentInterface {

    private lateinit var question: SingleChoiceQuestion

    private var atLeastOneHasBeenChecked = false

    private lateinit var answerId: String
    private lateinit var answerLabel: String

    companion object {
        fun newInstance(question: SingleChoiceQuestion): SingleChoiceFragment {
            val fragment = SingleChoiceFragment()
            val args = Bundle()
            args.putParcelable("question", question)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.single_choice, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        question = arguments!!.getParcelable("question")

        questionTitle.text = question.title

        for(choice in question.choices){

            val rb = layoutInflater.inflate(R.layout.radio_button, null) as RadioButton
            rb.id = choice.id.toInt()
            rb.text = choice.label
            radioGroup.addView(rb)
        }

        RxRadioGroup
            .checkedChanges(radioGroup)
            .filter{ radioButtonId -> radioButtonId != -1 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                radioButtonId ->

                val rb = radioGroup.findViewById<RadioButton>(radioButtonId)

                answerId = rb.id.toString()
                answerLabel = rb.text.toString()

                atLeastOneHasBeenChecked = rb.isChecked

                if(atLeastOneHasBeenChecked) {
                    (activity as MainActivity).enableNextButton()
                    return@subscribe
                }
                (activity as MainActivity).disableNextButton()
            })
    }

    override fun validate() = atLeastOneHasBeenChecked

    override fun getAnswer() = DehydratedAnswer(answerId,answerLabel)

    override fun getQuestion() = DehydratedQuestion(question.id,question.title)
}