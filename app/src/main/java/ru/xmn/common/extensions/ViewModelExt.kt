package ru.xmn.common.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

inline fun <reified VM : ViewModel> LifecycleOwner.viewModelProvider(key: String, crossinline viewModel: () -> VM)
        = lazyFast { provideViewModel(key, viewModel) }

inline fun <reified VM : ViewModel> LifecycleOwner.viewModelProvider(crossinline viewModel: () -> VM)
        = lazyFast { provideViewModel(viewModel) }

inline fun <reified VM : ViewModel> LifecycleOwner.provideViewModel(key: String, crossinline viewModel: () -> VM): VM {
    val viewModelProviderFactory = viewModelProviderFactory(viewModel)
    return createProvider(this, viewModelProviderFactory).get(key, VM::class.java)
}

inline fun <reified VM : ViewModel> LifecycleOwner.provideViewModel(crossinline viewModel: () -> VM): VM {
    val viewModelProviderFactory = viewModelProviderFactory(viewModel)
    return createProvider(this, viewModelProviderFactory).get(VM::class.java)
}

inline fun <reified VM : ViewModel> viewModelProviderFactory(crossinline viewModel: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = viewModel() as T
    }
}

fun createProvider(lifecycleOwner: LifecycleOwner, factory: ViewModelProvider.Factory): ViewModelProvider {
    return when (lifecycleOwner) {
        is Fragment -> ViewModelProviders.of(lifecycleOwner, factory)
        is FragmentActivity -> ViewModelProviders.of(lifecycleOwner, factory)
        else -> throw IllegalArgumentException("Worked only with Fragment and FragmentActivity")
    }
}

inline fun <reified VM : ViewModel> LifecycleOwner.viewModelProvider(key: String? = null)
        = lazyFast {
    provideViewModel<VM>(key)
}

inline fun <reified VM : ViewModel> LifecycleOwner.provideViewModel(key: String?): VM {
    val provider = createProvider(this)
    return when (key) {
        null -> provider.get(VM::class.java)
        else -> provider.get(key, VM::class.java)
    }
}

fun createProvider(lifecycleOwner: LifecycleOwner): ViewModelProvider {
    return when (lifecycleOwner) {
        is Fragment -> ViewModelProviders.of(lifecycleOwner)
        is FragmentActivity -> ViewModelProviders.of(lifecycleOwner)
        else -> throw IllegalArgumentException("Worked only with Fragment and FragmentActivity")
    }
}

