package br.com.rodrigohsb.kyc.viewpager.webservice

import br.com.rodrigohsb.kyc.viewpager.webservice.payload.QuestionPayload
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @rodrigohsb
 */
interface WebServiceAPI {

    @GET("/questions")
    fun fetchQuestions(): Observable<List<QuestionPayload>>

    @POST("/answers")
    fun sendAnswers(): Observable<String>

}