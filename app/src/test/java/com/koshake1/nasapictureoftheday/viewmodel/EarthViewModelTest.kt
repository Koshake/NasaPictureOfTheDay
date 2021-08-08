package com.koshake1.nasapictureoftheday.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.koshake1.nasapictureoftheday.TestCoroutineRule
import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.repository.RepositoryEarthImpl
import com.koshake1.nasapictureoftheday.retrofit.data.EarthServerResponseData
import com.koshake1.nasapictureoftheday.ui.earth.EarthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class EarthViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel : EarthViewModel

    private val repository = Mockito.mock(RepositoryEarthImpl::class.java)

    @Before
    fun setUp() {
        viewModel = EarthViewModel(repository)

    }

    @Test
    fun viewModel_ReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<EarthData> {}
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
            val observer = Observer<EarthData> {}
            val liveData = viewModel.subscribeToLiveData()

            Mockito.`when`(repository.sendServerRequest(LocalDate.now().toString(), API_KEY)).thenReturn(
                listOf<EarthServerResponseData>())
            try {
                liveData.observeForever(observer)
                viewModel.handleServerRequest(LocalDate.now().toString())
                val value: EarthData.Error = liveData.value as EarthData.Error
                Assert.assertEquals(value.error.message, EXCEPTION_TEXT)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun viewModel_returnsNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<EarthData> {}
            val liveData = viewModel.subscribeToLiveData()

            Mockito.`when`(repository.sendServerRequest(LocalDate.now().toString(), API_KEY)).thenReturn(
                    null)
            try {
                liveData.observeForever(observer)
                viewModel.handleServerRequest(LocalDate.now().toString())
                val value: EarthData.Error = liveData.value as EarthData.Error
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