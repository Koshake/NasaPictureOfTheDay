package com.koshake1.nasapictureoftheday.ui.picture

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.koshake1.nasapictureoftheday.R
import com.koshake1.nasapictureoftheday.data.POD.PictureOfTheDayData
import com.koshake1.nasapictureoftheday.di.injectDependencies
import com.koshake1.nasapictureoftheday.ui.MainActivity
import com.koshake1.nasapictureoftheday.ui.earth.ActivityEarth
import com.koshake1.nasapictureoftheday.ui.notes.NotesActivity
import com.koshake1.nasapictureoftheday.ui.settings.SettingsActivity
import com.koshake1.nasapictureoftheday.utils.*
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.chipGroup
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.*

class PictureOfTheDayFragment : Fragment() {

    companion object {
        private val TAG = "tag"
        fun newInstance() = PictureOfTheDayFragment()
    }

    private lateinit var viewModel : PictureOfTheDayViewModel

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        initViewModel()
        return inflater.inflate(R.layout.main_fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "POD onViewCreated ")

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        setBottomAppBar(view)
        setChips()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_info -> {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> bottomSheetBehavior.state =
                        BottomSheetBehavior.STATE_EXPANDED
                    BottomSheetBehavior.STATE_HIDDEN -> bottomSheetBehavior.state =
                        BottomSheetBehavior.STATE_EXPANDED
                    else -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            R.id.app_bar_settings ->
                startActivity(Intent(requireActivity(), SettingsActivity::class.java))
            R.id.app_bar_notes -> {
                startActivity(Intent(requireActivity(), NotesActivity::class.java))
            }
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(
                        it.supportFragmentManager,
                        TAG
                    )
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(data: PictureOfTheDayData?) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                loadPicture(serverResponseData.url)
                title_view.text = serverResponseData.title
                data_view.text = serverResponseData.date
                setBottomSheetTitle(serverResponseData.title)
                setBottomSheetText(serverResponseData.explanation)
            }
            is PictureOfTheDayData.Loading -> {
            }
            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
                Log.d(TAG, data.error.message.toString())
            }
        }
    }

    private fun setBottomAppBar(view: View) {
        if (activity is MainActivity) {
            val context = activity as MainActivity
            val bottomAppBar: BottomAppBar = view.findViewById(R.id.bottom_app_bar)
            context.setSupportActionBar(bottomAppBar)
            context.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            bottomAppBar.setNavigationIcon(R.drawable.ic_earth)
            bottomAppBar.setNavigationOnClickListener {
                activity?.let { startActivity(Intent(it, ActivityEarth::class.java)) }
            }
            setHasOptionsMenu(true)
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        TransitionManager.beginDelayedTransition(bottom_sheet_container)
                        bottom_sheet_description_info.visibility = TextView.VISIBLE
                        bottom_sheet_description_header.visibility = TextView.GONE
                        bottom_sheet_description.visibility = TextView.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        TransitionManager.beginDelayedTransition(bottom_sheet_container)
                        bottom_sheet_description_info.visibility = TextView.GONE
                        bottom_sheet_description_header.visibility = TextView.VISIBLE
                        bottom_sheet_description.visibility = TextView.VISIBLE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        TransitionManager.beginDelayedTransition(bottom_sheet_container)
                        bottom_sheet_description_info.visibility = TextView.GONE
                        bottom_sheet_description_header.visibility = TextView.VISIBLE
                        bottom_sheet_description.visibility = TextView.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun initViewModel() {
        val model: PictureOfTheDayViewModel by currentScope.inject()
        viewModel = model
        viewModel.subscribeToLiveData()
            .observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.handleServerRequest(LocalDate.now().toString())
    }

    private fun loadPicture(url: String?) {
        if (url.isNullOrEmpty()) {
            toast("Url is Null")
        } else {
            image_view.load(url) {
                lifecycle(this@PictureOfTheDayFragment)
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
                //toast("Picture is loaded")
            }
        }
    }

    private fun setBottomSheetTitle(text: String?) {
        text.let { bottom_sheet_description_header.text = text }
    }

    private fun setBottomSheetText(text: String?) {
        text?.let { bottom_sheet_description.text = text }
    }

    private fun setChips() {
        chipToday.isChecked = true
        chipGroup.setOnCheckedChangeListener { chipGroup, position ->
            chipGroup.findViewById<Chip>(position)?.let {
                when (it) {
                    chipToday -> viewModel.handleServerRequest(LocalDate.now().toString())
                    chipYesterday -> viewModel.handleServerRequest(LocalDate.now().minusDays(1).toString())
                    else -> Unit
                }
            }
        }
    }
}