package br.com.rodrigohsb.kyc.viewpager.webservice

import br.com.rodrigohsb.kyc.viewpager.KYCApplication
import br.com.rodrigohsb.kyc.viewpager.transformer.SchedulerTransformer

/**
 * @rodrigohsb
 */
class Service {

    private val webServiceAPI by lazy { KYCApplication.mWebServiceAPI }

    fun fetchQuestions() = webServiceAPI
                           .fetchQuestions()
                           .compose(SchedulerTransformer())

    fun sendAnswers() = webServiceAPI
                        .sendAnswers()
                        .compose(SchedulerTransformer())
}