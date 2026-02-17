package com.example.employeelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeelist.data.Employee
import com.example.employeelist.repository.EmployeeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest


class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {

    private val _employees = MutableStateFlow<Flow<List<Employee>>>(repository.getEmployees())

    @OptIn(ExperimentalCoroutinesApi::class)
    val employees = _employees.flatMapLatest { it }

    fun generateEmployees() {
        viewModelScope.launch {
            repository.clear()

            val list = List(20) {
                Employee(
                    name = generateRandomName(),
                    age = Random.nextInt(21, 60),
                    salary = Random.nextDouble(30000.0, 120000.0),
                    company = "Company-${Random.nextInt(1, 100)}",
                    married = Random.nextBoolean()
                )
            }

            repository.insertEmployees(list)
        }
    }

    fun sortBySalary() {
        _employees.value = repository.getEmployeesSortedBySalary()
    }

    fun filterMarried() {
        _employees.value = repository.getMarriedEmployees()
    }

    fun showAll() {
        _employees.value = repository.getEmployees()
    }


    private fun generateRandomName(): String {
        val chars = ('A'..'Z') + ('a'..'z')
        return (1..7)
            .map { chars.random() }
            .joinToString("")
    }
}
