package com.david.hackro.androidlearning

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

/**
 * @see
 * https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html
 */

class CoroutineViewModel : ViewModel() {


    // Job and Dispatcher are combined into a CoroutineContext which
    // will be discussed shortly
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private val itemList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    /**
     * @see
     * Perform long-running/memory intensive operations asynchronously
     * Are lightweight threads
     * Solve problems related concurrency
     * Eliminations of chaining callbacks / callback-hell
     * Exeptions and Cancelation handling
     * Sequential execution
     * Scope Execution
     * Coroutines Types are: Launch / Async
     */
    fun coroutines() {
        //Coroutines Types
        launchCoroutine()
        asyncCoroutine()

        //Dispatchers Types
        dispatchers()
    }

    /**
     * @see
     * Launch Coroutine
     * Fire and forget
     * Return a Job
     * Could get status/cancel using job object
     * Need handle the exception
     * could get crash the app
     */
    private fun launchCoroutine() {
        val job = GlobalScope.launch(Dispatchers.Default) {
            try {
                itemList.count() //any operation
            } catch (e: java.lang.Exception) { // need to handle the exceptions
                Log.e("exception from launch coroutine", e.message.toString())
            }
        }

        Log.e("the job from launch coroutine is active", "${job.isActive}") // we could get status
        job.cancel() //we could cancel
        Log.e("the job from launch coroutine is active", "${job.isActive}")
        Log.e("the job from launch coroutine is Complete", "${job.isCompleted}")
    }

    /**
     * @see
     * Perform a task and return a result (Deferred)
     * Return a Deferred
     * Could get status/cancel using Deferred object
     * Could apply await operation over Deferred object to get the value
     * Exception is store inside the deferred
     */
    private fun asyncCoroutine() = scope.launch {
        val deferredJob = GlobalScope.async(Dispatchers.Default) {
            itemList.count()
        }

        Log.e(
            "the deferred from async coroutine is active", "${deferredJob.isActive}"
        ) // we could get status
        deferredJob.cancel() //we could cancel
        Log.e("the deferred from async coroutine is active", "${deferredJob.isActive}")
        Log.e("the deferred from async coroutine is Complete", "${deferredJob.isCompleted}")
        Log.e("the value emitted from async coroutine is", "${deferredJob.await()}")
    }

    private fun dispatchers() {
        val defaultDispatcherJob = GlobalScope.async(Dispatchers.Default) {
            /** Perform CPU-intensive operations */
        }

        val ioDispatcherJob = GlobalScope.async(Dispatchers.IO) {
            /** Perform I/O operations, usually network request and fetch data */
        }

        val mainDispatcherJob = GlobalScope.async(Dispatchers.Main) {
            /** Executed on the main thread, could get block it */
        }

        /**
         * withContext is allowed us to easily change context
         * https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/with-context.html
         */
        val ioDispatcherWithContextJob = GlobalScope.async(Dispatchers.IO) {
            /** Execution on the separated thread*/
            /** Execution on the main thread */
            withContext(Dispatchers.Main) {
                /** Execution on the main thread */
            }
        }

    }
}