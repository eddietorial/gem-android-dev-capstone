package com.gem.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// The JSON shape returned by the Little Lemon API. 
@Serializable
data class MenuNetwork(
    @SerialName("menu") val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")          val id: Int,
    @SerialName("title")       val title: String,
    @SerialName("description") val description: String,
    @SerialName("price")       val price: String,
    @SerialName("image")       val image: String,
    @SerialName("category")    val category: String
)

// Maps a network model to a Room entity so the two layers stay decoupled.
fun MenuItemNetwork.toMenuItemEntity() = MenuItemEntity(
    id          = id,
    title       = title,
    description = description,
    price       = price,
    image       = image,
    category    = category
)
