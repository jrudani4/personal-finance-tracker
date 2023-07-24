var totalIncome = [[${totalIncome}]];
var totalExpense = [[${totalExpense}]];

var ctx = document.getElementById('incomeVsExpenseChart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ['Income', 'Expense'],
        datasets: [{
            data: [totalIncome, totalExpense],
            backgroundColor: [
                'rgba(75, 192, 192, 0.2)',
                'rgba(255, 99, 132, 0.2)',
            ],
            borderColor: [
                'rgba(75, 192, 192, 1)',
                'rgba(255, 99, 132, 1)',
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive: true, // Adjust as needed
        maintainAspectRatio: true, // Adjust as needed
        title: {
            display: true,
            text: 'Income vs. Expense'
        }
    }
});