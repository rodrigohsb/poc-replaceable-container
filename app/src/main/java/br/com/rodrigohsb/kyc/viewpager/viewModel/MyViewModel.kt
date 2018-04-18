package br.com.rodrigohsb.kyc.viewpager.viewModel

import android.arch.lifecycle.ViewModel
import br.com.rodrigohsb.kyc.viewpager.State
import br.com.rodrigohsb.kyc.viewpager.handler.StateHandler
import br.com.rodrigohsb.kyc.viewpager.webservice.Service

/**
 * @rodrigohsb
 */
class MyViewModel: ViewModel() {

    private val service by lazy { Service() }

    fun searchContent() = service
                            .fetchQuestions()
                            .compose(StateHandler())
                            .startWith(State.Loading())
}