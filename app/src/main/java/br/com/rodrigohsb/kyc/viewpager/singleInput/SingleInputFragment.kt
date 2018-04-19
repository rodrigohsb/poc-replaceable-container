package br.com.rodrigohsb.kyc.viewpager.singleInput

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

    private lateinit var editText: EditText

    private lateinit var question: SingleInputQuestion

    private val validator = SingleInputValidator()

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

        questionTitle.text = question.title

        editText = layoutInflater.inflate(R.layout.comp_edittext, null) as EditText
        editText.id = question.id.toInt()
        editText.hint = question.hint

        single_input_root.addView(editText)

        val set1 = ConstraintSet()
        set1.clone(single_input_root)
        set1.connect(editText.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        set1.connect(editText.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        set1.connect(editText.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        set1.connect(editText.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)
        set1.applyTo(single_input_root)

        RxTextView
            .textChanges(editText)
            .map { charSequence -> !charSequence.isEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ hasContent ->
                when (hasContent){
                    true -> (activity as MainActivity).enableNextButton()
                    false -> (activity as MainActivity).disableNextButton()
                }
            })
    }

    override fun validate() = validator.isValid(editText.text.toString())

    override fun getAnswer() = DehydratedAnswer(editText.id.toString(),editText.text.toString())

    override fun getQuestion() = DehydratedQuestion(question.id,question.title)
}