package dev.supergooey.mongoose

import android.app.Application
import dev.supergooey.mongoose.settings.ReaderSettings

class App : Application() {

    val readerSettings: ReaderSettings by lazy {
        ReaderSettings(this)
    }
}
