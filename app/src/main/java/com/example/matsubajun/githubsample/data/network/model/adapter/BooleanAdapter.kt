package com.example.matsubajun.githubsample.data.network.model.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson

class BooleanAdapter {

    @ToJson
    fun toJson(@IntBool boolean: Boolean): String {
        return boolean.toString()
    }

    @Throws(JsonDataException::class)
    @FromJson
    @IntBool
    fun fromJson(reader: JsonReader): Boolean {
        if (reader.peek() == JsonReader.Token.NUMBER) {
            return reader.nextInt() == 1
        } else if (reader.peek() == JsonReader.Token.BOOLEAN) {
            return reader.nextBoolean()
        }
        return false
    }
}

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class IntBool