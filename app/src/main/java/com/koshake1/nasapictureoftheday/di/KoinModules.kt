package com.koshake1.nasapictureoftheday.di

import com.koshake1.nasapictureoftheday.data.notes.NotesData
import com.koshake1.nasapictureoftheday.data.notes.NotesRepository
import com.koshake1.nasapictureoftheday.data.notes.NotesRepositoryImpl
import com.koshake1.nasapictureoftheday.repository.RepositoryEarth
import com.koshake1.nasapictureoftheday.repository.RepositoryEarthImpl
import com.koshake1.nasapictureoftheday.repository.RepositoryPOD
import com.koshake1.nasapictureoftheday.repository.RepositoryPODImpl
import com.koshake1.nasapictureoftheday.retrofit.data.PODRetrofitImpl
import com.koshake1.nasapictureoftheday.retrofit.data.RetrofitImpl
import com.koshake1.nasapictureoftheday.ui.earth.EarthFragment
import com.koshake1.nasapictureoftheday.ui.earth.EarthViewModel
import com.koshake1.nasapictureoftheday.ui.notes.NoteFragment
import com.koshake1.nasapictureoftheday.ui.notes.NoteViewModel
import com.koshake1.nasapictureoftheday.ui.notes.NotesFragment
import com.koshake1.nasapictureoftheday.ui.notes.NotesViewModel
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayFragment
import com.koshake1.nasapictureoftheday.ui.picture.PictureOfTheDayViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, pictureScreen, earthScreen, notesScreen))
}

val application = module {

    single {
        PODRetrofitImpl()
    } bind RetrofitImpl::class

    single {
        RepositoryPODImpl(get())
    } bind RepositoryPOD::class

    single {
        RepositoryEarthImpl(get())
    } bind RepositoryEarth::class
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

val notesScreen = module {

    single {
        NotesRepositoryImpl()
    } bind NotesRepository::class

    scope(named<NotesFragment>()) {
        viewModel {
            NotesViewModel(get())
        }
    }

    scope(named<NoteFragment>()) {
        viewModel { (note: NotesData?) ->
            NoteViewModel(get(), note)
        }
    }
}