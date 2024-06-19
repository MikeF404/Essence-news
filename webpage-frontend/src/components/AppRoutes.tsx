import React, {useContext, useState} from 'react';
import { Route, Routes } from 'react-router-dom';
import Feed from '@/components/Feed';
import { Article } from '@/types/article';
import {GlobalStateContext} from "@/components/GlobalStateContext.tsx";

const AppRoutes: React.FC = () => {
    const [popularArticles, setPopularArticles] = useState<Article[]>([]);
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);
    const { updateReadArticlesCount } = useContext(GlobalStateContext)!;
    const userId = localStorage.getItem('essence-news-user-id');

    updateReadArticlesCount();
    return (
        <Routes>
            <Route path="/popular" element={<Feed endpoint="http://localhost:8080/api/articles/popular" articles={popularArticles} setArticles={setPopularArticles} />} />
            <Route path="/personalized" element={<Feed endpoint={`http://localhost:8080/api/articles/relevant/${userId}/0`} articles={personalizedArticles} setArticles={setPersonalizedArticles} />} />
            <Route path="/archived" element={<Feed endpoint={`http://localhost:8080/api/user/${userId}/read-articles`} articles={archivedArticles} setArticles={setArchivedArticles} />} />
            <Route path="/" element={<Feed endpoint="http://localhost:8080/api/articles/popular" articles={popularArticles} setArticles={setPopularArticles} />} />
        </Routes>
    );
};

export default AppRoutes;