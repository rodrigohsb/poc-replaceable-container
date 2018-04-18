package br.com.rodrigohsb.kyc.viewpager.domain.question

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class Choice (val id: String, val label: String): Parcelable