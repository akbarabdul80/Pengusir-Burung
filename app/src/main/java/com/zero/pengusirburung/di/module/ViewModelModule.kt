package com.zero.pengusirburung.di.module

import com.zero.pengusirburung.root.RootViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RootViewModel() }
}