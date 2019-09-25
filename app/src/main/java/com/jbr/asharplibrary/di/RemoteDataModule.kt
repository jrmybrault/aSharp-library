package com.jbr.asharplibrary.di

import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jbr.asharplibrary.musicbrainz.api.MBArtistAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val remoteDataModule = module {

    single<MBArtistAPI> {
        get<Retrofit>().create(MBArtistAPI::class.java)
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://musicbrainz.org/ws/2/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }

    single<Gson> {
        GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Date::class.java, MBDateDeserializer())
            .create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single<HttpLoggingInterceptor> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        loggingInterceptor
    }

    single<Interceptor> {
        Interceptor { chain ->
            val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()

            chain.proceed(request)
        }
    }
}

private class MBDateDeserializer : JsonDeserializer<Date> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
        dateFormats.forEach { format ->
            try {
                return SimpleDateFormat(format, Locale.getDefault()).parse(json.asString)
            } catch (_: ParseException) {
            }
        }

        throw JsonParseException("Cannot deserialize dateFrom: ${json.asString} : unsupported format.")
    }

    companion object {
        private val dateFormats = listOf("yyyy-MM-dd", "yyyy")
    }
}