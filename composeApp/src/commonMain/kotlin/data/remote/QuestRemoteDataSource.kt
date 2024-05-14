package data.remote

import co.touchlab.kermit.Logger
import com.lequest.app.BuildKonfig
import data.remote.model.QuestDto
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

interface QuestRemoteDataSource {
    suspend fun getAllQuests(): List<QuestDto>
}

class QuestRemoteDataSourceImpl : QuestRemoteDataSource {

    private val supabase = createSupabaseClient(
        supabaseUrl = "https://eixvhrxtnjbbosjdcarz.supabase.co",
        supabaseKey = BuildKonfig.SUPABASE_API_KEY,
    ) {
        install(Postgrest)
    }

    override suspend fun getAllQuests(): List<QuestDto> {
        val list = supabase.from("quests").select().decodeList<QuestDto>()
        Logger.withTag(this::class.simpleName ?: "").d { "Quests from supabase: $list" }
        return list
    }
}