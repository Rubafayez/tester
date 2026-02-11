#!/bin/bash

# ==========================================
# 🛑 IMPORTANT: MODIFY THESE VALUES FIRST 🛑
# ==========================================

# 1. Your Database Password (change "your_password" to your actual postgres password)
export DB_PASSWORD=""

# 2. Your Database User (usually "postgres", change if different)
export DB_USER="postgres"

# 3. Your Database URL (usually localhost:5432/moviedb)
export DB_URL="jdbc:postgresql://localhost:5432/moviedb"

# ==========================================

echo "🚀 Starting Fabflix Backend..."
echo "👤 User: $DB_USER"
echo "🔗 URL:  $DB_URL"
echo ""

# Run the Spring Boot application using Maven Wrapper
./mvnw spring-boot:run
