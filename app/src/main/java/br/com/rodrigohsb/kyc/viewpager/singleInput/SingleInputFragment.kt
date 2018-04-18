package br.com.rodrigohsb.kyc.viewpager.singleInput

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import br.com.rodrigohsb.kyc.viewpager.R
import br.com.rodrigohsb.kyc.viewpager.domain.answer.DehydratedAnswer
import br.com.rodrigohsb.kyc.viewpager.domain.question.DehydratedQuestion
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleInputQuestion
import br.com.rodrigohsb.kyc.viewpager.manager.ContentInterface
import br.com.rodrigohsb.kyc.viewpager.ui.MainActivity
import br.com.rodrigohsb.kyc.viewpager.validate.Validator
import kotlinx.android.synthetic.main.single_input.*

/**
 * @rodrigohsb
 */
class SingleInputFragment: Fragment(), Validator, ContentInterface {

    private lateinit var editText: EditText

    private lateinit var question: SingleInputQuestion

    companion object {
        fun newInstance(question: SingleInputQuestion): SingleInputFragment {
            val fragment = SingleInputFragment()
            val args = Bundle()
            args.putParcelable("question", question)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.single_input, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        question = arguments!!.getParcelable<SingleInputQuestion>("question")

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

        editText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                if(s.isEmpty()){
                    (activity as MainActivity).disableNextButton()
                    return
                }
                (activity as MainActivity).enableNextButton()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun validate(): Boolean {
        return if(editText.text.toString().isEmpty()){
            Toast.makeText(activity,"Por favor, insira uma resposta válida", Toast.LENGTH_SHORT).show()
            false
        }
        else{
            true
        }
    }

    override fun getAnswer() = DehydratedAnswer(editText.id.toString(),editText.text.toString())

    override fun getQuestion() = DehydratedQuestion(question.id,question.title)
}