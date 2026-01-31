package dev.supergooey.mongoose

import android.app.Application
import dev.supergooey.mongoose.data.local.AppDatabase
import dev.supergooey.mongoose.data.network.ApiClient
import dev.supergooey.mongoose.data.repository.ItemRepository

class App : Application() {

    val database: AppDatabase by lazy {
        AppDatabase.create(this)
    }

    val apiClient: ApiClient by lazy {
        ApiClient.create()
    }

    val itemRepository: ItemRepository by lazy {
        ItemRepository(
            itemDao = database.itemDao(),
            apiClient = apiClient
        )
    }
}
