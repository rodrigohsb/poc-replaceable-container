package br.com.rodrigohsb.kyc.viewpager.manager

import br.com.rodrigohsb.kyc.viewpager.domain.answer.DehydratedAnswer
import br.com.rodrigohsb.kyc.viewpager.domain.question.DehydratedQuestion

/**
 * @rodrigohsb
 */
interface ContentInterface {

    fun getQuestion(): DehydratedQuestion

    fun getAnswer(): DehydratedAnswer

}