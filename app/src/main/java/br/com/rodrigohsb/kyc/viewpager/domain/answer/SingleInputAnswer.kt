package br.com.rodrigohsb.kyc.viewpager.domain.answer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class SingleInputAnswer (val text: String)
                              : Answer,
                              Parcelable