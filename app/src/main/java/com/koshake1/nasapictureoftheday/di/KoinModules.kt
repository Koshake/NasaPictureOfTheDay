package com.koshake1.nasapictureoftheday.di

import androidx.lifecycle.MutableLiveData
import com.koshake1.nasapictureoftheday.data.POD.PictureOfTheDayData
import com.koshake1.nasapictureoftheday.data.earth.EarthData
import com.koshake1.nasapictureoftheday.retrofit.data.PODRetrofitImpl
import com.koshake1.nasapictureoftheday.ui.earth.EarthFragment
import com.koshake1.nasapictureoftheday.ui.earth.EarthViewModel
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayFragment
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, pictureScreen, earthScreen))
}

val application = module {

    single {
        PODRetrofitImpl()
    }
}

val pictureScreen = module {
    scope(named<PictureOfTheDayFragment>()) {
        viewModel {
            PictureOfTheDayViewModel(get())
        }
    }
}

val earthScreen = module {
    scope(named<EarthFragment>()) {
        viewModel {
            EarthViewModel(get())
        }
    }
}