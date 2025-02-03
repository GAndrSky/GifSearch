package repository

import models.GiphyResponse
import network.GiphyApi

class GiphyRepository(private val api: GiphyApi) {

    // Твой API ключ (замени на свой)
    companion object {
        const val API_KEY = "3pV5FKyHxvOdiL5KjsZ5lyakxl8Sb5SX"
    }

    suspend fun searchGifs(query: String, offset: Int = 0): GiphyResponse {
        return api.searchGifs(
            apiKey = API_KEY,
            query = query,
            offset = offset
        )
    }
}
