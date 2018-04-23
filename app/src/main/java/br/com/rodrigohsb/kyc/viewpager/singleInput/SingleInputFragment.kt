package br.com.rodrigohsb.kyc.viewpager.singleInput

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigohsb.kyc.viewpager.R
import br.com.rodrigohsb.kyc.viewpager.domain.answer.DehydratedAnswer
import br.com.rodrigohsb.kyc.viewpager.domain.question.DehydratedQuestion
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleInputQuestion
import br.com.rodrigohsb.kyc.viewpager.manager.ContentInterface
import br.com.rodrigohsb.kyc.viewpager.ui.MainActivity
import br.com.rodrigohsb.kyc.viewpager.validate.Validator
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.single_input.*

/**
 * @rodrigohsb
 */
class SingleInputFragment: Fragment(), Validator, ContentInterface {

    private lateinit var question: SingleInputQuestion

    private val validator by lazy { SingleInputValidator() }

    companion object {
        fun newInstance(question: SingleInputQuestion): SingleInputFragment {
            val fragment = SingleInputFragment()
            val args = Bundle()
            args.putParcelable("question", question)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
                              : View? {
        return inflater.inflate(R.layout.single_input, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        question = arguments!!.getParcelable("question")

        single_input_question.text = question.title

        single_input_answer.id = question.id.toInt()
        single_input_answer.hint = question.hint

        RxTextView
        .textChanges(single_input_answer)
        .map { charSequence -> !charSequence.isEmpty() }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ hasContent ->
            when (hasContent){
                true -> (activity as MainActivity).enableNextButton()
                false -> (activity as MainActivity).disableNextButton()
            }
        })
    }

    override fun validate() = validator.isValid(single_input_answer.text.toString())

    override fun getAnswer() = DehydratedAnswer(single_input_answer.id.toString(), single_input_answer.text.toString())

    override fun getQuestion() = DehydratedQuestion(question.id,question.title)
}