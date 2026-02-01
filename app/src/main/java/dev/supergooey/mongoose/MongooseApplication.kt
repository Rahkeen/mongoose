package dev.supergooey.mongoose

import android.app.Application
import dev.supergooey.mongoose.settings.ReaderSettings

class MongooseApplication : Application() {

    val readerSettings: ReaderSettings by lazy {
        ReaderSettings(this)
    }
}
