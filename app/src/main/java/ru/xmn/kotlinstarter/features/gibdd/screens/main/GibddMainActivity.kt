package ru.xmn.kotlinstarter.features.gibdd.screens.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gibdd_main.*
import ru.xmn.common.extensions.viewModelProvider
import ru.xmn.common.observeNonNull
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.application.App
import ru.xmn.kotlinstarter.features.gibdd.model.FinesDataDao
import javax.inject.Inject

class GibddMainActivity : AppCompatActivity() {
    private val gibddMainViewModel: GibddMainViewModel by viewModelProvider()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gibdd_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.document_toolbar_title)
        gibddMainViewModel.gibddMainScreen.observeNonNull(this) {
            driving_license_number.text = it.drivingLicense
            car_doc_number.text = it.carDocs
            car_reg_number.text = it.carReg
        }
    }

}

class GibddMainViewModel : ViewModel() {
    val gibddMainScreen: MutableLiveData<FinesData> = MutableLiveData()

    @Inject lateinit var finesDataDao: FinesDataDao

    init {
        App.component.gibddComponent().build().inject(this)
        gibddMainScreen.value = FinesData(
                finesDataDao.drivingLicense,
                finesDataDao.carDocs,
                finesDataDao.carReg)
    }
}

class FinesData(val drivingLicense: String,
                val carDocs: String,
                val carReg: String)