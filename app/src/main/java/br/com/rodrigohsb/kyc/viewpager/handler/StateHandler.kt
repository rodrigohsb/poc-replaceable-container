package br.com.rodrigohsb.kyc.viewpager.handler

import br.com.rodrigohsb.kyc.viewpager.State
import br.com.rodrigohsb.kyc.viewpager.domain.question.Choice
import br.com.rodrigohsb.kyc.viewpager.domain.question.Question
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleChoiceQuestion
import br.com.rodrigohsb.kyc.viewpager.domain.question.SingleInputQuestion
import br.com.rodrigohsb.kyc.viewpager.webservice.payload.QuestionPayload
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * @rodrigohsb
 */
class StateHandler: ObservableTransformer<Any, State> {
    override fun apply(upstream: Observable<Any>): ObservableSource<State> {
        return upstream
                .map {

                    val questionPayload = it as List<QuestionPayload>

                    val questions = ArrayList<Question>()

                    var question: Question? = null

                    for(payload in questionPayload){
                        when(payload.type){

                            "single-input" -> {
                                question = SingleInputQuestion(payload.id, payload.mandatory, payload.title, "^[a-ZA-Z]{50}", "ex: João Silva da Costa")
                            }

                            "single-choice" -> {

                                val choices = ArrayList<Choice>()

                                choices.add(Choice("1", "Masculino"))
                                choices.add(Choice("2", "Feminino"))
                                choices.add(Choice("3", "Faz diferença?"))

                                question = SingleChoiceQuestion(payload.id, payload.mandatory, payload.title, choices)
                            }
                        }
                        question?.let{
                            questions.add(it)
                        }
                    }

                    State.Sucess(questions) as State
                }
                .onErrorResumeNext { error: Throwable ->
                    Observable.just(State.Error())
                }
    }

}