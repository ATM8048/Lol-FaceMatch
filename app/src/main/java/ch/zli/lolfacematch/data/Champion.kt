package ch.zli.lolfacematch.data

import java.io.Serializable

data class Champion(
    val id: String,
    val name: String,
    val title: String,
    val tags: List<String>,
) : Serializable
