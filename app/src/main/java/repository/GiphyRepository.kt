package repository

import models.GiphyResponse
import network.GiphyApi
import models.RandomGifResponse

class GiphyRepository(private val api: GiphyApi) {

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

    suspend fun getRandomGif(): RandomGifResponse {
        return api.getRandomGif(
            apiKey = API_KEY
        )
    }
}
