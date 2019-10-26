package com.daredevil.twitchauth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.daredevil.mvitest.util.ApiEmptyResponse
import com.daredevil.mvitest.util.ApiErrorResponse
import com.daredevil.mvitest.util.ApiResponse
import com.daredevil.mvitest.util.ApiSuccessResponse
import com.daredevil.twitchauth.util.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.Loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.Success(newData))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(Resource.Loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    GlobalScope.launch(IO) {
                        saveCallResult(processResponse(response))
                        withContext(Main) {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.Success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    GlobalScope.launch(Main) {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.Success(newData))
                        }
                    }

                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.Error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract fun saveCallResult(item: RequestType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): LiveData<ResultType>

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}