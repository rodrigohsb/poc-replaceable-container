package br.com.rodrigohsb.kyc.viewpager.domain.question

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class SingleInputQuestion (val id: String,
                                val mandatory: Boolean,
                                val title: String,
                                val validation: String,
                                val hint: String)
                                : Question,
                                Parcelable