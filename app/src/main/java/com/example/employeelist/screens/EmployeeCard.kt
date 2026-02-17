package com.example.employeelist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.employeelist.data.Employee
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.tooling.preview.Preview
@Preview(showBackground = true)
@Composable
fun EmployeeListPreview() {
    val employees = listOf(
        Employee(1, "Karthik", 30, 50000.0, "Google", true),
    )

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(employees.size) { index ->
                    EmployeeCard(employee = employees[index])
                }
            }
        }
    }
}
@Composable
fun EmployeeCard(employee: Employee) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = "ID: ${employee.id}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Name: ${employee.name}")
            Text(text = "Age: ${employee.age}")
            Text(text = "Salary: ₹${(employee.salary)}")
            Text(text = "Company: ${employee.company}")
            Text(text = "Married: ${if (employee.married) "Yes" else "No"}")
        }
    }
}
