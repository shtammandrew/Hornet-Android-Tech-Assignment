package com.hornet.movies.data.model.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetails(
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    val credits: Credits = Credits()
) {
    val actors: List<Person>
        get() = credits.cast.take(3)

    val director: Person?
        get() = credits.crew.firstOrNull { it.department == "Directing" }

    val productionCompany: ProductionCompany?
        get() = productionCompanies.firstOrNull()
}

@JsonClass(generateAdapter = true)
data class Credits(
    val cast: List<Person> = emptyList(),
    val crew: List<Person> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int = 0,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Person(
    val id: Int = 0,
    val name: String = "",
    val department: String? = "",
    @Json(name = "profile_path")
    val profilePath: String? = ""
)
