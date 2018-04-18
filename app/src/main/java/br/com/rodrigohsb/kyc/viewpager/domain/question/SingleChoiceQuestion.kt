package br.com.rodrigohsb.kyc.viewpager.domain.question

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class SingleChoiceQuestion (val id: String,
                                 val mandatory: Boolean,
                                 val title: String,
                                 val choices: List<Choice>)
                                 : Question,
                                 Parcelable