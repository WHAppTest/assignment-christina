package at.willhaben.applicants_test_project.dependency

import android.content.Context
import at.willhaben.applicants_test_project.usecase.useCaseModule
import at.willhaben.applicants_test_project.usecasemodel.useCaseModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinWrapper {
    private val appModules: List<Module> = listOf(useCaseModule, useCaseModelModule)

    fun start(applicationContext: Context) {
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}