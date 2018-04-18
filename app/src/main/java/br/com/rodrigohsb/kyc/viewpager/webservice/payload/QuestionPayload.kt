package br.com.rodrigohsb.kyc.viewpager.webservice.payload

/**
 * @rodrigohsb
 */
data class QuestionPayload (val id: String,
                            val mandatory: Boolean,
                            val title: String,
                            val type: String)