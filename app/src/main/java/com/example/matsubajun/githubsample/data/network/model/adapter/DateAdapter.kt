package com.example.matsubajun.githubsample.data.network.model.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonAdapter.Factory
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter : JsonAdapter<Date>() {

    companion object {
        val FACTORY = Factory { type, _, _ ->
            return@Factory if (type === Date::class.java) {
                DateAdapter()
            } else {
                null
            }
        }
    }

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")

        return dateFormat.parse(reader.nextString())
    }

    @Throws(IOException::class)
    override fun toJson(
            writer: JsonWriter,
            value: Date?
    ) {
        writer.value(value?.toString())
    }

    override fun toString(): String {
        return "JsonAdapter(Date)"
    }
}