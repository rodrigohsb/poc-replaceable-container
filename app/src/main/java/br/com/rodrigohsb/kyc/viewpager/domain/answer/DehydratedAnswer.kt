package br.com.rodrigohsb.kyc.viewpager.domain.answer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class DehydratedAnswer (val id: String,
                             val text: String)
                             : Parcelable