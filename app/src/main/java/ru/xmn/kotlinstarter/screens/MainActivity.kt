package ru.xmn.kotlinstarter.screens

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Subcomponent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.xmn.kotlinstarter.R

import kotlinx.android.synthetic.main.activity_main.*
import ru.xmn.common.extensions.gone
import ru.xmn.common.extensions.visible
import ru.xmn.kotlinstarter.application.App
import ru.xmn.kotlinstarter.application.di.scopes.ActivityScope
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

class MainActivity : AppCompatActivity() {
    lateinit var abstractViewModel: AbstractViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupViewModel()
    }

    private fun setupViewModel() {
        abstractViewModel = ViewModelProviders.of(this).get(AbstractViewModel::class.java)
        abstractViewModel.abstractLiveData.observe(this, Observer {
            when (it) {
                is AbstractState.Loading -> showLoading()
                is AbstractState.Success -> showValue(it.value)
                is AbstractState.Error -> showError()
            }
        })
    }

    private fun showValue(value: String) {
        loading.gone()
        valueTextView.text = value
    }

    private fun showError() {
        loading.gone()
        valueTextView.text = "error"
    }

    private fun showLoading() {
        loading.visible()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
    }

}

class AbstractViewModel : ViewModel() {

    @Inject
    lateinit var abstractInteractor: AbstractInteractor

    val abstractLiveData: MutableLiveData<AbstractState> = MutableLiveData()

    init {
        App.component.abstractInteractorComponent().provideModule(AbstractModule()).build().inject(this)
        abstractInteractor.getValue()
                .map { AbstractState.Success(it) as AbstractState }
                .toFlowable()
                .startWith(AbstractState.Loading)
                .onErrorReturn { AbstractState.Error(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{abstractLiveData.value = it}
    }
}

sealed class AbstractState {
    object Loading : AbstractState()
    data class Success(val value: String) : AbstractState()
    data class Error(val error: Throwable) : AbstractState()
}

@Module
class AbstractModule

@ActivityScope
@Subcomponent(modules = arrayOf(AbstractModule::class))
interface AbstractComponent {
    fun inject(abstractViewModel: AbstractViewModel)

    @Subcomponent.Builder
    interface Builder {
        fun build(): AbstractComponent
        fun provideModule(r: AbstractModule): Builder

    }
}

@ActivityScope
class AbstractInteractor
@Inject constructor(val abstractRepository: AbstractRepository) {
    fun getValue(): Single<String> {
        return abstractRepository.getValue()
    }

}

@Singleton
class AbstractRepository
@Inject constructor()
{
    fun getValue(): Single<String> {
        return Single.just("value").delaySubscription<Single<String>>(3000, TimeUnit.MILLISECONDS)
    }
}
