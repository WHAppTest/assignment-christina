package at.willhaben.applicants_test_project.usecasemodel

import at.willhaben.applicants_test_project.usecasemodel.detail.DetailUseCaseModel
import at.willhaben.applicants_test_project.usecasemodel.list.ListUseCaseModel
import org.koin.dsl.module

val useCaseModelModule = module {
    factory { ListUseCaseModel() }
    factory { DetailUseCaseModel() }
}