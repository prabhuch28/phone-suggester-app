#!/bin/bash

echo "🚀 Deploying Phone Suggester Application..."

# Build the application
echo "📦 Building application..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🌐 Your application is ready for deployment!"
    echo ""
    echo "📋 Deployment Options:"
    echo "1. Render.com (Recommended - Free):"
    echo "   - Go to https://render.com"
    echo "   - Connect your GitHub repository"
    echo "   - Select this project"
    echo "   - Deploy automatically"
    echo ""
    echo "2. Railway.app:"
    echo "   - Go to https://railway.app"
    echo "   - Connect your GitHub repository"
    echo "   - Deploy automatically"
    echo ""
    echo "3. Heroku:"
    echo "   - Install Heroku CLI"
    echo "   - Run: heroku create"
    echo "   - Run: git push heroku main"
    echo ""
    echo "📁 Files ready for deployment:"
    echo "   - pom.xml"
    echo "   - render.yaml"
    echo "   - Procfile"
    echo "   - system.properties"
    echo ""
    echo "🔗 After deployment, your app will be available at:"
    echo "   - Render: https://your-app-name.onrender.com"
    echo "   - Railway: https://your-app-name.railway.app"
    echo "   - Heroku: https://your-app-name.herokuapp.com"
else
    echo "❌ Build failed! Please check the errors above."
    exit 1
fi 