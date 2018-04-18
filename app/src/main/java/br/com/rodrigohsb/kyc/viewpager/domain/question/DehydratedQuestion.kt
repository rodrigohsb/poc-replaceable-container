package br.com.rodrigohsb.kyc.viewpager.domain.question

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class DehydratedQuestion (val id: String,
                               val text: String)
                               : Parcelable