package net.byteabyte.mobiletv.core.tvshows.details

import net.byteabyte.mobiletv.core.tvshows.AuthorId
import net.byteabyte.mobiletv.core.tvshows.ImagesMap

data class Author(
    val id: AuthorId,
    val name: String,
    val profileImages: ImagesMap
)