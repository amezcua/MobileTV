package net.byteabyte.mobiletv.toprated

import net.byteabyte.mobiletv.SharedTransitions
import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.top_rated.TopRatedShow

data class TopRatedClickData(
    val topRatedShow: TopRatedShow,
    val topRatedDisplayedImage: ImageUrl?,
    val sharedTransitions: SharedTransitions
)