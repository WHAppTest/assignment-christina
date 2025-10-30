package at.willhaben.applicants_test_project.usecasemodel

import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCaseModel<T : Any> : KoinComponent, CoroutineScope {
    private val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected val channel: Channel<T> = Channel(Channel.RENDEZVOUS)

    protected var lastState: T? = null

    fun getUIChannel(): ReceiveChannel<T> = channel

    abstract fun saveState(bundle: Bundle)

    abstract fun restoreState(bundle: Bundle)
}