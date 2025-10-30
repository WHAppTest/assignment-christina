package at.willhaben.applicants_test_project

import android.app.Application
import android.os.StrictMode
import at.willhaben.applicants_test_project.dependency.KoinWrapper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )

        KoinWrapper.start(this)
    }
}