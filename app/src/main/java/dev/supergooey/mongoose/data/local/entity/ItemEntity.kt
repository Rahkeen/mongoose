package dev.supergooey.mongoose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.supergooey.mongoose.models.Item

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String
) {
    fun toItem(): Item = Item(
        id = id,
        title = title,
        description = description
    )

    companion object {
        fun fromItem(item: Item): ItemEntity = ItemEntity(
            id = item.id,
            title = item.title,
            description = item.description
        )
    }
}
