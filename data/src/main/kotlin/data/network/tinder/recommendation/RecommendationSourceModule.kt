package data.network.tinder.recommendation

import com.nytimes.android.external.store3.base.Fetcher
import com.nytimes.android.external.store3.base.impl.FluentStoreBuilder
import com.nytimes.android.external.store3.base.impl.StalePolicy
import com.nytimes.android.external.store3.base.impl.Store
import com.nytimes.android.external.store3.middleware.moshi.MoshiParserFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import data.network.ParserModule
import data.network.tinder.TinderApi
import data.network.tinder.TinderApiModule
import okio.BufferedSource
import java.util.*
import javax.inject.Singleton
import dagger.Lazy as DaggerLazy

/**
 * Module used to provide stuff required by TopRequestSource objects.
 */
@Module(includes = arrayOf(ParserModule::class, TinderApiModule::class))
internal class RecommendationSourceModule {
    @Provides
    @Singleton
    fun store(moshiBuilder: Moshi.Builder, api: TinderApi) =
            FluentStoreBuilder.parsedWithKey<Unit, BufferedSource, RecommendationResponse>(
                    Fetcher { _ -> fetch(api) }) {
                parsers = listOf(MoshiParserFactory.createSourceParser(moshiBuilder
                                .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                                .build(),
                                RecommendationResponse::class.java))
                stalePolicy = StalePolicy.NETWORK_BEFORE_STALE
            }

    @Provides
    @Singleton
    fun source(store: DaggerLazy<Store<RecommendationResponse, Unit>>)
            = RecommendationSource(store)

    private fun fetch(api: TinderApi) = api.getRecommendations().map { it.source() }
}
