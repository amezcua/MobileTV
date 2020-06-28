package net.byteabyte.mobiletv

import net.byteabyte.mobiletv.core.tvshows.ImageUrl
import net.byteabyte.mobiletv.core.tvshows.paged.ShowSummary

data class ShowClickData(
    val showSummary: ShowSummary,
    val displayedImage: ImageUrl?,
    val sharedTransitions: SharedTransitions
)