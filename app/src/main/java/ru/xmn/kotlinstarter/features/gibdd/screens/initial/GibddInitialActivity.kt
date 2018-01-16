package ru.xmn.kotlinstarter.features.gibdd.screens.initial

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_gibdd_initial.*
import kotlinx.android.synthetic.main.layout_registration_data_screen.view.*
import ru.xmn.common.observeNonNull
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.application.App
import android.text.InputFilter
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import ru.xmn.common.extensions.*
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.dependencies.InitialScreenDependencies
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.RegistrationDataViewModel
import ru.xmn.kotlinstarter.features.gibdd.screens.initial.registration_data_screen.VerifiedState
import ru.xmn.kotlinstarter.features.gibdd.screens.main.GibddMainActivity


class GibddInitialActivity : AppCompatActivity() {
    val initialNavigation: InitialNavigation by viewModelProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gibdd_initial)
        setupViewModel()
    }

    private fun setupViewModel() {
        initialNavigation.screenState.observeNonNull(this) {
            when (it) {
                ScreenState.Start -> {
                    inflateScreen(R.layout.layout_gibdd_start) {
                        findViewById<Button>(R.id.next).setOnClickListener { initialNavigation.goNext() }
                    }
                }
                ScreenState.CarReg -> {
                    inflateRegistrationDataScreen(
                            R.drawable.car_reg,
                            App.component.gibddComponent().build().carRegDependencies())
                }
                ScreenState.CarDocs -> {
                    inflateRegistrationDataScreen(
                            R.drawable.car_doc,
                            App.component.gibddComponent().build().carDocsDependencies())
                }
                ScreenState.DrivingLicense -> {
                    inflateRegistrationDataScreen(
                            R.drawable.car_doc,
                            App.component.gibddComponent().build().drivingLicenseDependencies())
                }
                ScreenState.MainScreen -> {
                    gotoMainScreen()
                }
            }
        }
    }

    private fun inflateRegistrationDataScreen(imageRes: Int, dependencies: InitialScreenDependencies) {
        inflateScreen(R.layout.layout_registration_data_screen) {
            val registrationViewModel = viewModelForRegistrationDataScreen(this, dependencies)

            number.apply {
                hint = dependencies.hintText
                filters = arrayOf<InputFilter>(InputFilter.AllCaps())
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(text: Editable?) {
                        text?.let {
                            registrationViewModel.onTextChange(text.toString())
                        }
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                })
                setText(dependencies.registrationNumberDao.get())
                requestFocus()
                showKeyboard()
            }

            description.text = dependencies.descriptionText
            top_image.setImageResource(imageRes)
            findViewById<Button>(R.id.next).setOnClickListener {
                registrationViewModel.save(number.text.toString())
                initialNavigation.goNext()
            }

            skip.setOnClickListener {
                var dialog: AlertDialog? = null
                dialog = alert("Пропустить", "Хотите пропустить ввод данных?") {
                    yesButton { gotoMainScreen() }
                    noButton { dialog?.cancel() }
                }.show()
            }

            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(p0: View?) {
                    registrationViewModel.verifiedState.removeObservers(this@GibddInitialActivity)
                }

                override fun onViewAttachedToWindow(p0: View?) {
                }
            })
        }
    }

    private fun viewModelForRegistrationDataScreen(view: View, dependencies: InitialScreenDependencies): RegistrationDataViewModel {
        val registrationViewModel = provideViewModel<RegistrationDataViewModel>(dependencies.uniqueKey) {
            RegistrationDataViewModel(
                    dependencies.textLength,
                    dependencies.verify,
                    dependencies.registrationNumberDao
            )
        }
        registrationViewModel.verifiedState.observeNonNull(this) {
            val nextButton = view.findViewById<Button>(R.id.next)
            when (it) {
                VerifiedState.ShortText -> {
                    nextButton.isEnabled = false
                    view.number.error = null
                }
                VerifiedState.Verified -> {
                    nextButton.isEnabled = true
                    view.number.error = null
                }
                VerifiedState.NotVerified -> {
                    nextButton.isEnabled = false
                    view.number.error = "Введены неверные данные"
                }
            }
        }
        return registrationViewModel
    }

    private fun gotoMainScreen() {
        startActivity<GibddMainActivity>()
    }

    private fun inflateScreen(@LayoutRes layoutRes: Int, init: View.() -> Unit = {}): View {
        val view = container.inflate(layoutRes)
        container.removeAllViews()
        container.addView(view)
        view.init()
        return view
    }

    override fun onBackPressed() {
        if (!initialNavigation.goBack())
            super.onBackPressed()
    }
}



