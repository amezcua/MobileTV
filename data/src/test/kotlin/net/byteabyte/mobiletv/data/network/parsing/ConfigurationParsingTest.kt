package net.byteabyte.mobiletv.data.network.parsing

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.byteabyte.mobiletv.data.network.retrofit.configuration.JsonConfigurationResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ConfigurationParsingTest {
    @Test
    fun `Configuration responses can be parsed into configuration objects`() {
        val adapter = buildMoshiAdapter()
        val json = adapter.fromJson(configurationResponse)!!
        val configuration = json.toConfigurationNetworkResponse()

        Assertions.assertEquals("https://image.tmdb.org/t/p/", configuration.baseUrl)
    }

    private fun buildMoshiAdapter() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(JsonConfigurationResponse::class.java)

    private val configurationResponse =
        """
{
    "images": {
        "base_url": "http://image.tmdb.org/t/p/",
        "secure_base_url": "https://image.tmdb.org/t/p/",
        "backdrop_sizes": [
            "w300",
            "w780",
            "w1280",
            "original"
        ],
        "logo_sizes": [
            "w45",
            "w92",
            "w154",
            "w185",
            "w300",
            "w500",
            "original"
        ],
        "poster_sizes": [
            "w92",
            "w154",
            "w185",
            "w342",
            "w500",
            "w780",
            "original"
        ],
        "profile_sizes": [
            "w45",
            "w185",
            "h632",
            "original"
        ],
        "still_sizes": [
            "w92",
            "w185",
            "w300",
            "original"
        ]
    },
    "change_keys": [
        "adult",
        "air_date",
        "also_known_as",
        "alternative_titles",
        "biography",
        "birthday",
        "budget",
        "cast",
        "certifications",
        "character_names",
        "created_by",
        "crew",
        "deathday",
        "episode",
        "episode_number",
        "episode_run_time",
        "freebase_id",
        "freebase_mid",
        "general",
        "genres",
        "guest_stars",
        "homepage",
        "images",
        "imdb_id",
        "languages",
        "name",
        "network",
        "origin_country",
        "original_name",
        "original_title",
        "overview",
        "parts",
        "place_of_birth",
        "plot_keywords",
        "production_code",
        "production_companies",
        "production_countries",
        "releases",
        "revenue",
        "runtime",
        "season",
        "season_number",
        "season_regular",
        "spoken_languages",
        "status",
        "tagline",
        "title",
        "translations",
        "tvdb_id",
        "tvrage_id",
        "type",
        "video",
        "videos"
    ]
}
""".trimIndent()
}