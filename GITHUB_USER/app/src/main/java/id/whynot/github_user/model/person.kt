package id.whynot.github_user.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class person(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable