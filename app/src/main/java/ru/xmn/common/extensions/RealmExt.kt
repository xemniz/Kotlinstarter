package ru.xmn.common.extensions

fun List<String>?.serialize() = this?.joinToString(separator = "~~~~")?:""
fun String?.deserialize() = this?.split("~~~~")?: emptyList()
class sdf{}
