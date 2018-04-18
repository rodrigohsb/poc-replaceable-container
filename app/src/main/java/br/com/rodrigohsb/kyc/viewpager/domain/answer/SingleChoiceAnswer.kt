package br.com.rodrigohsb.kyc.viewpager.domain.answer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class SingleChoiceAnswer (val id: String,
                               val label: String)
                               : Answer,
                               Parcelable