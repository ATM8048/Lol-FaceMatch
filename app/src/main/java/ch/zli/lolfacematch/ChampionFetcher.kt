package ch.zli.lolfacematch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChampionFetcher {
    private var api: ChampionAPI
    private var champions: List<Champion> = emptyList() // 🛠️ Jetzt änderbar

    init {
        val retrofit =
            Retrofit
                .Builder()
                .baseUrl("https://ddragon.leagueoflegends.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(ChampionAPI::class.java)
    }

    fun fetchChampions(callback: (List<Champion>?) -> Unit) {
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

    fun getRandomChampion(
        smile: Float,
        leftEyeOpen: Float,
        rightEyeOpen: Float,
        headTiltY: Float,
    ): Champion? { // 🛠️ Kann jetzt `null` zurückgeben
        if (champions.isEmpty()) return null // 🚨 Falls Champions noch nicht geladen sind, vermeide Absturz!

        val filteredChampions =
            when {
                smile > 0.7 -> champions.filter { "Support" in it.tags || "Mage" in it.tags }
                headTiltY < -10 -> champions.filter { "Fighter" in it.tags || "Tank" in it.tags }
                leftEyeOpen > 0.5 && rightEyeOpen > 0.5 -> champions.filter { "Assassin" in it.tags }
                else -> champions
            }

        return filteredChampions.ifEmpty { champions }.random()
    }
}
