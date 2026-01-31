package dev.supergooey.mongoose.extensions

import android.content.Context
import dev.supergooey.mongoose.App
import dev.supergooey.mongoose.data.repository.ItemRepository

val Context.app: App
    get() = applicationContext as App

val Context.itemRepository: ItemRepository
    get() = app.itemRepository
