# Essence News Summarizer

## Introduction
Essence News Summarizer is a web application designed to provide quick and insightful summaries of news articles from various sources, promoting an engaging overview of daily news.

## Features
- **Article Summarization**: Summarize news articles into a concise format.
- **User Customization**: Users can customize the news feed according to their interests.
- **Responsive Design**: Optimized for both desktop and mobile devices.
- **API Integration**: Fetches articles and uses OpenAI's GPT-4o for generating summaries.

## Technologies
- **Backend**: Java, Spring Boot
- **Frontend**: React.js, Vue, Tailwind, Shadcn.ui
- **APIs**: OpenAI GPT-4o, NewsAPI.ai
- **Database**: PostgreSQL

## Getting Started

### Prerequisites
Before you begin, ensure you have met the following requirements:
- Java 11 or higher
- Node.js and npm
- PostgreSQL

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/MikeF404/Essence-news.git
   cd Essence-news
   ```
2. **Backend setup**
   Navigate to the backend directory and run:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
3. **Frontend Setup**
   In a new terminal, navigate to the frontend directory and run:
   ```bash
   cd frontend
   npm install
   npm run dev

5. **Environment Variables**
   In the root directory create a file env.properties and insert the environment variables in the following format:
   ```bash
   DB_USERNAME=[your DB username]
   DB_PASSWORD=[your DB password]
   DB_NAME=[your DB name]
   newsapi.key=[your NewsAPI.AI API key]
   openaiapi.key=[your ChatGPT API key]

### Usage
Once both React and Java Spring Boot applications are running, open your web browser and navigate to http://localhost:5173 (or w/e the React says) to view the UI. You can interact with the application by selecting different news to see the summaries and with different interactions make the feed more personalized.

