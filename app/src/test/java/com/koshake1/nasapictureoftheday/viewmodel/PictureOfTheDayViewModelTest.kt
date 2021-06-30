package com.koshake1.nasapictureoftheday.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.koshake1.nasapictureoftheday.TestCoroutineRule
import com.koshake1.nasapictureoftheday.data.POD.PictureOfTheDayData
import com.koshake1.nasapictureoftheday.repository.RepositoryPODImpl
import com.koshake1.nasapictureoftheday.retrofit.data.PODServerResponseData
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.robolectric.annotation.Config
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class PictureOfTheDayViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel : PictureOfTheDayViewModel

    private val repository = Mockito.mock(RepositoryPODImpl::class.java)

    @Before
    fun setUp() {
        viewModel = PictureOfTheDayViewModel(repository)

    }

    @Test
    fun viewModel_ReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<PictureOfTheDayData> {}
            val liveData = viewModel.subscribeToLiveData()

            try {
                liveData.observeForever(observer)
                viewModel.handleServerRequest(LocalDate.now().toString())
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun viewModel_ThrowsException() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<PictureOfTheDayData> {}
            val liveData = viewModel.subscribeToLiveData()

            try {
                liveData.observeForever(observer)
                viewModel.handleServerRequest(LocalDate.now().toString())
                val value: PictureOfTheDayData.Error = liveData.value as PictureOfTheDayData.Error
                Assert.assertEquals(value.error.message, EXCEPTION_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }

        }
    }

    @Test
    fun viewModel_returnsNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<PictureOfTheDayData> {}
            val liveData = viewModel.subscribeToLiveData()

            `when`(repository.sendServerRequest(LocalDate.now().toString(), API_KEY)).thenReturn(
                PODServerResponseData(null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null)
            )
            try {
                liveData.observeForever(observer)
                viewModel.handleServerRequest(LocalDate.now().toString())
                val value: PictureOfTheDayData.Error = liveData.value as PictureOfTheDayData.Error
                Assert.assertEquals(value.error.message, EXCEPTION_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }

        }
    }

    @Test
    fun viewModel_ApiKeyIsBlank() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<PictureOfTheDayData> {}
            val liveData = viewModel.subscribeToLiveData()

            try {
                liveData.observeForever(observer)
                viewModel.handleServerRequest(LocalDate.now().toString())
                val value: PictureOfTheDayData.Error = liveData.value as PictureOfTheDayData.Error
                Assert.assertEquals(value.error.message, EXCEPTION_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }

        }
    }

    companion object {
        private const val EXCEPTION_TEXT = "Response is null or unsuccessful!"
        private const val API_KEY = "Key"
    }
}