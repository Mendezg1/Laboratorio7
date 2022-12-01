package gt.uvg.pokelist.model

data class PokeResponse (
    val count: Int = 0,
    val next: String = "",
    val previous: String? = "",
    val results: List<Resources> = listOf()
)

{
    data class Resources(
        val name: String,
        val url: String
    )
}