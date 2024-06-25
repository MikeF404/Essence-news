import React, {useContext, useState} from 'react';
import { Route, Routes } from 'react-router-dom';
import Feed from '@/components/Feed';
import { Article } from '@/types/article';
import {GlobalStateContext} from "@/components/GlobalStateContext.tsx";
import PopularFeed from "@/components/PopularFeed.tsx";

const AppRoutes: React.FC = () => {
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);

    const userId = localStorage.getItem('essence-news-user-id');

    return (
        <Routes>
            <Route path="/popular" element={<PopularFeed/>} />
            <Route path="/personalized" element={<Feed endpoint={`http://localhost:8080/api/articles/relevant/${userId}/0`} articles={personalizedArticles} setArticles={setPersonalizedArticles} />} />
            <Route path="/archived" element={<Feed endpoint={`http://localhost:8080/api/user/${userId}/read-articles`} articles={archivedArticles} setArticles={setArchivedArticles} />} />
            <Route path="/" element={<PopularFeed/>} />
        </Routes>
    );
};

export default AppRoutes;
