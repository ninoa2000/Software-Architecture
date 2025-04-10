const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const path = require('path');

// Create Express application
const app = express();
const PORT = 3000;

// Configure proxy middleware for Spring Boot API
const apiProxy = createProxyMiddleware({
  target: 'http://localhost:8080',
  changeOrigin: true,
  pathRewrite: {
    '^/api': '/api' // no rewrite needed, just to be explicit
  }
});

// Configure proxy for the H2 console
const h2ConsoleProxy = createProxyMiddleware({
  target: 'http://localhost:8080',
  changeOrigin: true,
  pathRewrite: {
    '^/h2-console': '/h2-console' // no rewrite needed, just to be explicit
  }
});

// Add proxy middleware
app.use('/api', apiProxy);
app.use('/h2-console', h2ConsoleProxy);

// Serve static files
app.use(express.static('public'));

// Default route - serve index.html
app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Start the server
app.listen(PORT, () => {
  console.log(`Node.js server running on http://localhost:${PORT}`);
  console.log(`Proxying requests to Spring Boot on http://localhost:8080`);
}); 