package ru.xmn.common.extensions

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

inline fun <reified T> Moshi.fromJson(json: String?): T? {
    val jsonAdapter = this.adapter(T::class.java)
    return jsonAdapter.fromJson(json)
}

inline fun <reified T> Moshi.listFromJson(json: String?): List<T> {
    val listMyData: Type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter: JsonAdapter<List<T>> = this.adapter(listMyData)
    return adapter.fromJson(json)
}

inline fun <reified T> T.toJson(): String {
    val jsonAdapter = Moshi.Builder().build().adapter(T::class.java)
    return jsonAdapter.toJson(this)
}