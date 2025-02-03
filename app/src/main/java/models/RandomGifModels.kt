package models

data class RandomGifResponse(
    val data: RandomGifData
)

data class RandomGifData(
    val id: String,
    val title: String,
    val images: RandomGifImages
)

data class RandomGifImages(
    val original: RandomGifImage
)

data class RandomGifImage(
    val url: String
)
