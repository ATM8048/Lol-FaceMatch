package ch.zli.lolfacematch.data

import retrofit2.Call
import retrofit2.http.GET

interface ChampionAPI {
    @GET("cdn/14.3.1/data/en_US/champion.json")
    fun getChampions(): Call<ChampionResponse>
}
