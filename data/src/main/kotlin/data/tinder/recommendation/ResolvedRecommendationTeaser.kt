package data.tinder.recommendation

internal data class ResolvedRecommendationTeaser constructor(
        val id: String,
        val description: String,
        val type: String) {
    companion object {
        val NONE = ResolvedRecommendationTeaser(id = "", description = "", type = "")
    }
}
