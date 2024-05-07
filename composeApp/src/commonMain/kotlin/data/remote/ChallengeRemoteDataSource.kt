package data.remote

import co.touchlab.kermit.Logger
import com.challenge.app.BuildKonfig
import data.remote.model.ChallengeDto
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

interface ChallengeRemoteDataSource {
    suspend fun getAllChallenges(): List<ChallengeDto>
}

class ChallengeRemoteDataSourceImpl : ChallengeRemoteDataSource {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://eixvhrxtnjbbosjdcarz.supabase.co",
        supabaseKey = BuildKonfig.SUPABASE_API_KEY,
    ) {
        install(Postgrest)
    }

    override suspend fun getAllChallenges(): List<ChallengeDto> {
        val list = supabase.from("quests").select().decodeList<ChallengeDto>()
        Logger.withTag(this::class.simpleName ?: "").d { "Challenges from supabase: $list" }
        return list
    }
}