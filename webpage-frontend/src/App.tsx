import React, {useContext, useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from '@/components/Header';
import {GlobalStateContext, GlobalStateProvider} from '@/components/GlobalStateContext';
import Feed from '@/components/Feed';
import { Article } from '@/types/article';
import {ThemeProvider} from "@/components/ThemeProvider.tsx";
import UserIdentifier from "@/components/UserIdentifier.tsx";

const App: React.FC = () => {
    return (
        <div className="p-20 flex justify-center bg-accent">
            <ThemeProvider defaultTheme="light" storageKey="essence-news-ui-theme">
                <GlobalStateProvider>

                    <Router>
                        <UserIdentifier />
                        <Header />
                        <AppRoutes />
                    </Router>

                </GlobalStateProvider>
            </ThemeProvider>
        </div>
    );
};

const AppRoutes: React.FC = () => {
    const [popularArticles, setPopularArticles] = useState<Article[]>([]);
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);
    const { userId }: string = localStorage.getItem("essence-news-user-id")
    console.log("userId: ", userId)

    return (


                <Routes>
                    <Route path="/popular" element={<Feed endpoint="http://localhost:8080/api/articles/popular" articles={popularArticles} setArticles={setPopularArticles} />} />
                    <Route path="/personalized" element={<Feed endpoint={`http://localhost:8080/api/articles/relevant/${userId}/0`} articles={personalizedArticles} setArticles={setPersonalizedArticles} />} />
                    <Route path="/archived" element={<Feed endpoint={`http://localhost:8080/api/user/${userId}/read-articles`} articles={archivedArticles} setArticles={setArchivedArticles} />} />
                    <Route path="/" element={<Feed endpoint="http://localhost:8080/api/articles/popular" articles={popularArticles} setArticles={setPopularArticles} />} />
                </Routes>


    );
};
export default App;

