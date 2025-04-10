import http.server
import socketserver
import os

PORT = 8000
DIRECTORY = os.path.join(os.path.dirname(os.path.abspath(__file__)), "public")

if not os.path.exists(DIRECTORY):
    os.makedirs(DIRECTORY)

with open(os.path.join(DIRECTORY, "index.html"), "w") as f:
    f.write("""
    <!DOCTYPE html>
    <html>
    <head>
        <title>Test Server</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
                padding: 20px;
                background-color: #f5f5f5;
            }
            .container {
                background-color: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            h1 {
                color: #333;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Test Server Working!</h1>
            <p>If you can see this page, the test web server is working correctly.</p>
            <p>This indicates that your network and browser configuration can access a local web server.</p>
        </div>
    </body>
    </html>
    """)

class Handler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=DIRECTORY, **kwargs)

httpd = socketserver.TCPServer(("", PORT), Handler)

print(f"Serving at http://localhost:{PORT}")
print(f"Press Ctrl+C to stop the server")
httpd.serve_forever() 