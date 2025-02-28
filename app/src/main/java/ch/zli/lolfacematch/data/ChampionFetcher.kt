package ch.zli.lolfacematch.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChampionFetcher {
    private var api: ChampionAPI
    private var champions: List<Champion> = emptyList()

    init {
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl("https://ddragon.leagueoflegends.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(ChampionAPI::class.java)
    }

    fun fetchChampionsData(callback: (List<Champion>?) -> Unit) {
        val call = api.getChampions()

        call.enqueue(
            object : Callback<ChampionResponse> {
                override fun onResponse(
                    call: Call<ChampionResponse>,
                    response: Response<ChampionResponse>,
                ) {
                    if (response.isSuccessful) {
                        champions = response
                            .body()
                            ?.data
                            ?.values
                            ?.toList() ?: emptyList()
                        callback(champions)
                    } else {
                        callback(null)
                    }
                }

                override fun onFailure(
                    call: Call<ChampionResponse>,
                    t: Throwable,
                ) {
                    callback(null)
                }
            },
        )
    }

    fun getRandomChampion(faceFeatures: FaceFeatures): Champion {
        val filteredChampions =
            when {
                faceFeatures.smilingProbability > 0.7 -> champions.filter { "Support" in it.tags || "Mage" in it.tags }
                faceFeatures.headEulerAngleY < -10 -> champions.filter { "Fighter" in it.tags || "Tank" in it.tags }
                faceFeatures.leftEyeOpenProbability > 0.5 -> champions.filter { "Assassin" in it.tags }
                faceFeatures.rightEyeOpenProbability > 0.5 -> champions.filter { "Marksman" in it.tags }
                else -> champions
            }

        return filteredChampions.ifEmpty { champions }.random()
    }
}
