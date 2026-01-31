package dev.supergooey.mongoose.data.repository

import dev.supergooey.mongoose.data.local.dao.ItemDao
import dev.supergooey.mongoose.data.local.entity.ItemEntity
import dev.supergooey.mongoose.data.network.ApiClient
import dev.supergooey.mongoose.models.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemRepository(
    private val itemDao: ItemDao,
    private val apiClient: ApiClient
) {

    fun getAllItems(): Flow<List<Item>> {
        return itemDao.getAllItems().map { entities ->
            entities.map { it.toItem() }
        }
    }

    suspend fun getItemById(id: String): Item? {
        return itemDao.getItemById(id)?.toItem()
    }

    suspend fun saveItem(item: Item) {
        itemDao.insertItem(ItemEntity.fromItem(item))
    }

    suspend fun deleteItem(id: String) {
        itemDao.deleteItem(id)
    }
}
