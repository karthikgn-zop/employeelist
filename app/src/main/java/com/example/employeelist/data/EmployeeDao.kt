package com.example.employeelist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insertAll(employees: List<Employee>)

    @Query("SELECT * FROM employees")
    fun getAll(): Flow<List<Employee>>

    @Query("DELETE FROM employees")
    suspend fun clearAll()

    @Query("SELECT * FROM employees ORDER BY salary DESC")
    fun sortBySalary(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE married = 1")
    fun filterMarried(): Flow<List<Employee>>

    @Query("SELECT * FROM employees ORDER BY salary DESC")
    fun getEmployeesSortedBySalary(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE married = 1")
    fun getMarriedEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM employees")
    fun getEmployees(): Flow<List<Employee>>

}
