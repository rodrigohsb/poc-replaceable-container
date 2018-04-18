package br.com.rodrigohsb.kyc.viewpager

import br.com.rodrigohsb.kyc.viewpager.domain.question.Question

/**
 * @rodrigohsb
 */
sealed class State {

    class Loading : State()
    class Error : State()
    data class Sucess(val questions: List<Question>) : State()
}