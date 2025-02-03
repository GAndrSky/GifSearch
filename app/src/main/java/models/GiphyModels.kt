package models

data class GiphyResponse(
    val data: List<Gif>
)

data class Gif(
    val id: String,
    val title: String,
    val images: Images
)

data class Images(
    val original: Image
)

data class Image(
    val url: String
)
