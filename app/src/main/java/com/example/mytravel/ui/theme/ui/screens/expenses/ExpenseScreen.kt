package com.example.mytravel.ui.theme.ui.screens.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytravel.ui.theme.data.model.Contribution
import com.example.mytravel.ui.theme.data.model.Expense
import com.example.mytravel.ui.theme.ui.components.ErrorScreen
import com.example.mytravel.ui.theme.ui.components.LoadingScreen
import com.example.mytravel.ui.theme.ui.components.TravelVSBottomNavigation
import com.example.mytravel.ui.theme.ui.components.TravelVSTopAppBar
import com.example.travelvs.ui.theme.TravelVSTheme
import com.example.mytravel.ui.theme.utils.UiState
import java.text.NumberFormat
import java.util.*
import com.example.mytravel.R

@Composable
fun ExpenseScreen(
    tripId: String,
    viewModel: ExpenseViewModel = hiltViewModel(),
    navigateToMembers: () -> Unit,
    navigateToItinerary: () -> Unit,
    navigateToBookings: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val expensesState by viewModel.expensesState.collectAsState()
    val showExpenseDetails by viewModel.showExpenseDetails.collectAsState()
    val selectedExpense by viewModel.selectedExpense.collectAsState()
    
    LaunchedEffect(tripId) {
        viewModel.fetchExpenses(tripId)
    }
    
    Scaffold(
        topBar = {
            TravelVSTopAppBar(title = stringResource(R.string.screen_expenses))
        },
        bottomBar = {
            TravelVSBottomNavigation(
                currentRoute = "expenses",
                onNavigateToTrips = navigateToMembers,
                onNavigateToItinerary = navigateToItinerary,
                onNavigateToExpenses = { /* Already on this screen */ },
                onNavigateToBookings = navigateToBookings,
                onNavigateToProfile = navigateToProfile
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Add new expense */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_expense))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (expensesState) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Error -> ErrorScreen((expensesState as UiState.Error).message)
                is UiState.Success -> {
                    val expenses = (expensesState as UiState.Success<List<Expense>>).data
                    ExpensesContent(
                        expenses = expenses,
                        onExpenseClick = { viewModel.showExpenseDetails(it) }
                    )
                }
            }
        }
    }
    
    if (showExpenseDetails && selectedExpense != null) {
        ExpenseDetailsDialog(
            expense = selectedExpense!!,
            onDismiss = { viewModel.hideExpenseDetails() }
        )
    }
}

@Composable
fun ExpensesContent(
    expenses: List<Expense>,
    onExpenseClick: (Expense) -> Unit
) {
    val sharedExpenses = expenses.filter { it.isShared }
    val individualExpenses = expenses.filter { !it.isShared }
    
    // Calculate total and remaining budget
    val totalExpenses = expenses.sumOf { it.amount }
    val budget = 600.0 // This would come from the trip data in a real app
    val remaining = budget - totalExpenses
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BudgetSummary(budget = budget, remaining = remaining)
        }
        
        if (sharedExpenses.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.shared_expenses),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
            }
            
            items(sharedExpenses) { expense ->
                ExpenseItem(
                    expense = expense,
                    onClick = { onExpenseClick(expense) }
                )
            }
        }
        
        if (individualExpenses.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.individual_expenses),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
            }
            
            items(individualExpenses) { expense ->
                ExpenseItem(
                    expense = expense,
                    onClick = { onExpenseClick(expense) }
                )
            }
        }
    }
}

@Composable
fun BudgetSummary(budget: Double, remaining: Double) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.US) }
    val remainingColor = if (remaining >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.trip_budget),
                style = MaterialTheme.typography.h6
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.remaining),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = currencyFormatter.format(remaining),
                    style = MaterialTheme.typography.subtitle1,
                    color = remainingColor,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = (remaining / budget).toFloat().coerceIn(0f, 1f),
                modifier = Modifier.fillMaxWidth(),
                color = remainingColor
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpenseItem(expense: Expense, onClick: () -> Unit) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.US) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = expense.title,
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.caption
                )
            }
            
            Text(
                text = currencyFormatter.format(expense.amount),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ExpenseDetailsDialog(expense: Expense, onDismiss: () -> Unit) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale.US) }
    
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.expense_details),
                        style = MaterialTheme.typography.h6
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = expense.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(R.string.total))
                    Text(
                        text = currencyFormatter.format(expense.amount),
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.body1
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Paid By",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = expense.paidBy,
                    style = MaterialTheme.typography.body1
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = expense.date,
                    style = MaterialTheme.typography.body1
                )
                
                if (expense.isShared && !expense.contributions.isNullOrEmpty()) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    Text(
                        text = stringResource(R.string.contributions),
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    expense.contributions.forEach { contribution ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = contribution.userName)
                            Text(
                                text = currencyFormatter.format(contribution.amount),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensesContentPreview() {
    val previewExpenses = listOf(
        Expense(
            id = "1",
            title = "Dinner at The Italian Place",
            amount = 120.0,
            date = "2023-06-15",
            category = "Food",
            paidBy = "John Doe",
            isShared = true,
            contributions = listOf(
                Contribution("1", "Ethan", 60.0),
                Contribution("2", "Sophia", 60.0)
            )
        ),
        Expense(
            id = "2",
            title = "Museum tickets",
            amount = 50.0,
            date = "2023-06-16",
            category = "Activities",
            paidBy = "Jane Smith",
            isShared = true,
            contributions = listOf(
                Contribution("1", "Ethan", 25.0),
                Contribution("2", "Sophia", 25.0)
            )
        ),
        Expense(
            id = "3",
            title = "Souvenir",
            amount = 30.0,
            date = "2023-06-17",
            category = "Shopping",
            paidBy = "John Doe",
            isShared = false,
            contributions = null
        )
    )
    
    TravelVSTheme {
        Surface {
            ExpensesContent(
                expenses = previewExpenses,
                onExpenseClick = {}
            )
        }
    }
}
