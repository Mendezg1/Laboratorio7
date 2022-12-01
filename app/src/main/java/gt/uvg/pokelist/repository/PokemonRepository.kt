package gt.uvg.pokelist.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import gt.uvg.pokelist.model.PokemonAPIService
import gt.uvg.pokelist.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import gt.uvg.pokelist.model.PokeResponse

class PokemonRepository {

    private val pokelist = mutableListOf<Pokemon>(Pokemon(1,"bulbasaur"))
    // Obtain pokemon list from https://pokeapi.co/
    fun getPokemonList(): List<Pokemon> {

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(" https://pokeapi.co/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val service: PokemonAPIService = retrofit.create(PokemonAPIService::class.java)

        if(pokelist.size == 1){
            service.getFirst100Pokemon().enqueue(object: Callback<PokeResponse> {
                override fun onResponse(call: Call<PokeResponse>, response: Response<PokeResponse>) {
                    Log.i("RESPONSE DEL API", response.toString())

                    if(!response.isSuccessful){ return }

                    val body = response.body()!!
                    var i: Int = 1
                    pokelist.removeAt(0)
                    for(pokemon in body.results) {
                        pokelist.add(Pokemon(i++, pokemon.name))
                    }
                }

                override fun onFailure(call: Call<PokeResponse>, t: Throwable) {
                    Log.i("MainFragment", t.message ?: "Null Message")
                }
            })
        }

        return pokelist

    }
}