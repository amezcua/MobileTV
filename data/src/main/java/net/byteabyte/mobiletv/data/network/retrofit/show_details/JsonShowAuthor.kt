package net.byteabyte.mobiletv.data.network.retrofit.show_details

import com.squareup.moshi.Json
import net.byteabyte.mobiletv.data.network.show_details.ShowAuthorNetwork

data class JsonShowAuthor(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "profile_path") val profileImagePath: String?
) {
    fun toShowAuthorNetwork() = ShowAuthorNetwork(
        id = id,
        name = name.orEmpty(),
        profileImage = profileImagePath.orEmpty()
    )
}