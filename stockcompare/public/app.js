// Sample stock data generator
function generateStockData(symbol, startPrice, volatility, days) {
    const data = [];
    let currentPrice = startPrice;
    const today = new Date();
    
    for (let i = days; i >= 0; i--) {
        const date = new Date();
        date.setDate(today.getDate() - i);
        
        // Random price movement based on volatility
        const change = (Math.random() - 0.5) * volatility * currentPrice;
        currentPrice = Math.max(0.01, currentPrice + change);
        
        data.push({
            date: date.toISOString().split('T')[0],
            price: currentPrice
        });
    }
    
    return {
        symbol: symbol,
        name: getStockName(symbol),
        exchange: "NASDAQ",
        data: data
    };
}

function getStockName(symbol) {
    const stocks = {
        'AAPL': 'Apple Inc.',
        'MSFT': 'Microsoft Corporation',
        'GOOGL': 'Alphabet Inc.',
        'AMZN': 'Amazon.com, Inc.',
        'META': 'Meta Platforms, Inc.',
        'TSLA': 'Tesla, Inc.',
        'NVDA': 'NVIDIA Corporation',
        'NFLX': 'Netflix, Inc.'
    };
    
    return stocks[symbol] || symbol;
}

// Store for pre-generated data
const stockDataStore = {};

// Pre-generate some stock data
function initializeStockData() {
    const symbols = ['AAPL', 'MSFT', 'GOOGL', 'AMZN', 'META', 'TSLA', 'NVDA', 'NFLX'];
    const startPrices = {
        'AAPL': 175.0,
        'MSFT': 405.0,
        'GOOGL': 150.0,
        'AMZN': 180.0,
        'META': 485.0,
        'TSLA': 175.0,
        'NVDA': 925.0,
        'NFLX': 625.0
    };
    
    const volatilities = {
        'AAPL': 0.02,
        'MSFT': 0.015,
        'GOOGL': 0.025,
        'AMZN': 0.03,
        'META': 0.035,
        'TSLA': 0.05,
        'NVDA': 0.04,
        'NFLX': 0.03
    };
    
    symbols.forEach(symbol => {
        stockDataStore[symbol] = generateStockData(
            symbol, 
            startPrices[symbol] || 100.0, 
            volatilities[symbol] || 0.02, 
            365
        );
    });
}

// Function to get stock data for a specific date range
function getStockDataForDateRange(symbol, startDate, endDate) {
    if (!stockDataStore[symbol]) {
        return null;
    }
    
    const stockData = stockDataStore[symbol];
    const filteredData = stockData.data.filter(item => {
        return item.date >= startDate && item.date <= endDate;
    });
    
    return {
        symbol: stockData.symbol,
        name: stockData.name,
        exchange: stockData.exchange,
        data: filteredData
    };
}

// Function to compare stocks
function compareStocks() {
    const symbol1 = document.getElementById('symbol1').value.toUpperCase();
    const symbol2 = document.getElementById('symbol2').value.toUpperCase();
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;
    
    // Validation
    if (!symbol1 || !symbol2 || !startDate || !endDate) {
        alert('Please fill in all fields');
        return;
    }
    
    if (new Date(startDate) > new Date(endDate)) {
        alert('Start date cannot be after end date');
        return;
    }
    
    // Get stock data
    const stock1Data = getStockDataForDateRange(symbol1, startDate, endDate);
    const stock2Data = getStockDataForDateRange(symbol2, startDate, endDate);
    
    if (!stock1Data) {
        alert(`Stock data not available for ${symbol1}`);
        return;
    }
    
    if (!stock2Data) {
        alert(`Stock data not available for ${symbol2}`);
        return;
    }
    
    if (stock1Data.data.length === 0 || stock2Data.data.length === 0) {
        alert('No data available for the selected date range');
        return;
    }
    
    // Prepare data for charts
    const dates = stock1Data.data.map(item => item.date);
    const prices1 = stock1Data.data.map(item => item.price);
    const prices2 = stock2Data.data.map(item => item.price);
    
    // Save data for result page
    localStorage.setItem('comparisonData', JSON.stringify({
        stock1: {
            symbol: stock1Data.symbol,
            name: stock1Data.name,
            exchange: stock1Data.exchange
        },
        stock2: {
            symbol: stock2Data.symbol,
            name: stock2Data.name,
            exchange: stock2Data.exchange
        },
        dates: dates,
        prices1: prices1,
        prices2: prices2,
        startDate: startDate,
        endDate: endDate
    }));
    
    // Redirect to result page
    window.location.href = 'result.html';
}

// Format dates for display
function formatDate(dateStr) {
    const parts = dateStr.split('-');
    return `${parts[1]}/${parts[2]}/${parts[0].substring(2)}`;
}

// On result page load, display the comparison
function displayComparison() {
    const data = JSON.parse(localStorage.getItem('comparisonData'));
    if (!data) {
        document.body.innerHTML = '<div class="container mt-5"><div class="alert alert-danger">No comparison data found. Please go back to the <a href="compare.html">comparison page</a>.</div></div>';
        return;
    }
    
    // Display date range
    document.getElementById('dateRange').textContent = `${new Date(data.startDate).toLocaleDateString()} - ${new Date(data.endDate).toLocaleDateString()}`;
    
    // Display stock info
    document.getElementById('stock1Name').textContent = `${data.stock1.name} (${data.stock1.symbol})`;
    document.getElementById('stock1Exchange').textContent = `Exchange: ${data.stock1.exchange}`;
    document.getElementById('stock1Price').textContent = `Latest Price: $${data.prices1[data.prices1.length - 1].toFixed(2)}`;
    
    document.getElementById('stock2Name').textContent = `${data.stock2.name} (${data.stock2.symbol})`;
    document.getElementById('stock2Exchange').textContent = `Exchange: ${data.stock2.exchange}`;
    document.getElementById('stock2Price').textContent = `Latest Price: $${data.prices2[data.prices2.length - 1].toFixed(2)}`;
    
    // Format dates
    const formattedDates = data.dates.map(formatDate);
    
    // Create actual prices chart
    const actualCtx = document.getElementById('actualPricesChart').getContext('2d');
    new Chart(actualCtx, {
        type: 'line',
        data: {
            labels: formattedDates,
            datasets: [
                {
                    label: `${data.stock1.name} (${data.stock1.symbol})`,
                    data: data.prices1,
                    borderColor: 'rgb(75, 192, 192)',
                    backgroundColor: 'rgba(75, 192, 192, 0.1)',
                    tension: 0.1,
                    fill: true
                },
                {
                    label: `${data.stock2.name} (${data.stock2.symbol})`,
                    data: data.prices2,
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.1)',
                    tension: 0.1,
                    fill: true
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Actual Stock Prices (USD)'
                },
                tooltip: {
                    mode: 'index',
                    intersect: false,
                    callbacks: {
                        label: function(context) {
                            let label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            if (context.parsed.y !== null) {
                                label += '$' + context.parsed.y.toFixed(2);
                            }
                            return label;
                        }
                    }
                }
            },
            scales: {
                x: {
                    ticks: {
                        maxRotation: 45,
                        minRotation: 45
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Price ($)'
                    },
                    ticks: {
                        callback: function(value, index, values) {
                            return '$' + value.toFixed(2);
                        }
                    }
                }
            }
        }
    });
    
    // Create normalized chart
    const normalizedCtx = document.getElementById('normalizedChart').getContext('2d');
    
    // Normalize prices for better comparison (first day = 100)
    const normalizedPrices1 = data.prices1.map(price => (price / data.prices1[0]) * 100);
    const normalizedPrices2 = data.prices2.map(price => (price / data.prices2[0]) * 100);
    
    new Chart(normalizedCtx, {
        type: 'line',
        data: {
            labels: formattedDates,
            datasets: [
                {
                    label: `${data.stock1.name} (${data.stock1.symbol})`,
                    data: normalizedPrices1,
                    borderColor: 'rgb(75, 192, 192)',
                    backgroundColor: 'rgba(75, 192, 192, 0.1)',
                    tension: 0.1,
                    fill: true
                },
                {
                    label: `${data.stock2.name} (${data.stock2.symbol})`,
                    data: normalizedPrices2,
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.1)',
                    tension: 0.1,
                    fill: true
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Normalized Stock Performance (First Day = 100)'
                },
                tooltip: {
                    mode: 'index',
                    intersect: false
                }
            },
            scales: {
                x: {
                    ticks: {
                        maxRotation: 45,
                        minRotation: 45
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Normalized Price'
                    }
                }
            }
        }
    });
}

// Initialize data
initializeStockData(); 