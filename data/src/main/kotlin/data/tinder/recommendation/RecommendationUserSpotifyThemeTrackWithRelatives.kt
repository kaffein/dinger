package data.tinder.recommendation

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

internal class RecommendationUserSpotifyThemeTrackWithRelatives(
        @Embedded
        var recommendationUserSpotifyThemeTrackEntity: RecommendationUserSpotifyThemeTrackEntity,
        @Relation(parentColumn = "id", entityColumn = "recommendationUserSpotifyThemeTrackEntityId",
                entity
                = RecommendationUserSpotifyThemeTrackEntity_RecommendationUserSpotifyThemeTrackArtistEntity::class,
                projection = arrayOf("recommendationUserSpotifyThemeTrackArtistEntityId"))
        var artists: Set<String>) {
    constructor() : this(RecommendationUserSpotifyThemeTrackEntity(album = "",
            previewUrl = "",
            name = "",
            id = "",
            uri = ""),
            emptySet())
}
