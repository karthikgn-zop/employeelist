package com.example.employeelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.employeelist.data.EmployeeDatabase
import com.example.employeelist.repository.EmployeeRepository
import com.example.employeelist.screens.EmployeeCard
import com.example.employeelist.viewmodel.EmployeeViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EmployeeDatabase::class.java,
            "employee_db"
        ).build()
    }

    private val repository by lazy {
        EmployeeRepository(db.employeeDao())
    }

    private val viewModel: EmployeeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EmployeeViewModel(repository) as T
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val employees by viewModel.employees.collectAsState(initial = emptyList())
            var expanded by remember { mutableStateOf(false) }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                contentWindowInsets = WindowInsets.safeDrawing,

                topBar = {
                    TopAppBar(
                        title = { Text("Employee List") },
                        actions = {

                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.Sort,
                                    contentDescription = "Sort Options"
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {

                                DropdownMenuItem(
                                    text = { Text("Sort by Salary") },
                                    onClick = {
                                        viewModel.sortBySalary()
                                        expanded = false
                                    }
                                )

                                DropdownMenuItem(
                                    text = { Text("Filter Married") },
                                    onClick = {
                                        viewModel.filterMarried()
                                        expanded = false
                                    }
                                )

                                DropdownMenuItem(
                                    text = { Text("Show All") },
                                    onClick = {
                                        viewModel.showAll()
                                        expanded = false
                                    }
                                )
                            }
                        }
                    )
                },

                bottomBar = {
                    Surface(
                        modifier = Modifier.navigationBarsPadding(),
                        shadowElevation = 8.dp
                    ) {
                        Button(
                            onClick = { viewModel.generateEmployees() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Generate Employees")
                        }
                    }
                }

            ) { paddingValues ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(employees) { employee ->
                        EmployeeCard(employee)
                    }
                }
            }
        }
    }
}
