package net.byteabyte.mobiletv.data.network.imagesconfiguration

data class ImagesConfigurationNetwork(
    val baseUrl: String,
    val backDropSizes: List<String>,
    val posterSizes: List<String>
)