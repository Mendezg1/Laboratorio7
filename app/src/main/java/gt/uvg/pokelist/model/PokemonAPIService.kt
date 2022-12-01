package gt.uvg.pokelist.model

import retrofit2.http.GET
import retrofit2.Call

interface PokemonAPIService {
    @GET("pokemon?limit=100&offset=0")
    fun getFirst100Pokemon(): Call<PokeResponse>
}