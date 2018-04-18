package br.com.rodrigohsb.kyc.viewpager.domain

import android.os.Parcelable
import br.com.rodrigohsb.kyc.viewpager.Content
import kotlinx.android.parcel.Parcelize

/**
 * @rodrigohsb
 */
@Parcelize
data class Summary (val content: List<Content>): Parcelable