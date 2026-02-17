package com.example.employeelist.repository

import com.example.employeelist.data.Employee
import com.example.employeelist.data.EmployeeDao

class EmployeeRepository(private val dao: EmployeeDao) {

    fun getEmployees() = dao.getEmployees()

    fun getEmployeesSortedBySalary() = dao.getEmployeesSortedBySalary()

    fun getMarriedEmployees() = dao.getMarriedEmployees()

    suspend fun insertEmployees(list: List<Employee>) {
        dao.insertAll(list)
    }

    suspend fun clear() = dao.clearAll()
}
