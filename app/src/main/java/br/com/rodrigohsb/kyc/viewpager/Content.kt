package br.com.rodrigohsb.kyc.viewpager

import android.os.Parcelable
import br.com.rodrigohsb.kyc.viewpager.domain.answer.DehydratedAnswer
import br.com.rodrigohsb.kyc.viewpager.domain.question.DehydratedQuestion
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class Content (val question: DehydratedQuestion, val answer: DehydratedAnswer): Parcelable