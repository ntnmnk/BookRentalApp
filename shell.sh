#!/bin/bash

# Define the base URL of your API
BASE_URL="http://localhost:8080"

# Array of book data in JSON format
BOOK_DATA=(
    '{"title": "Book 1", "author": "Author 1", "isbn": "ISBN 1", "description": "Description 1"}'
    '{"title": "Book 2", "author": "Author 2", "isbn": "ISBN 2", "description": "Description 2"}'
    '{"title": "Book 3", "author": "Author 3", "isbn": "ISBN 3", "description": "Description 3"}'
    # Add more books as needed
)

# Iterate over the book data array and send POST requests to the API
for data in "${BOOK_DATA[@]}"
do
    curl -X POST -H "Content-Type: application/json" -d "$data" "$BASE_URL/api/books/all"
    echo "" # Add a newline for readability
done
