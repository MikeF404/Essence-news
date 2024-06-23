import React, { createContext, ReactNode, useEffect, useState } from 'react';
import axios from 'axios';
import {Article} from "@/types/article.ts";

interface GlobalStateContextProps {
    readArticlesCount: number;
    incrementReadArticlesCount: () => void;
    updateReadArticlesCount: () => void;
    personalizedFeedEnabled: boolean;
    userId: string | null;
    setUserId: (userId: string) => void;
    popularArticles: Article[];
    fetchPopularArticles: (page: number) => void;
    fetchMorePopularArticles: () => void;
    personalizedArticles: Article[];
    fetchPersonalizedArticles: (page: number) => void;
    fetchMorePersonalizedArticles: () => void;
    archivedArticles: Article[];
    fetchArchivedArticles: (page: number) => void;
    fetchMoreArchivedArticles: () => void;
}

const GlobalStateContext = createContext<GlobalStateContextProps | undefined>(undefined);

interface GlobalStateProviderProps {
    children: ReactNode;
}

const GlobalStateProvider: React.FC<GlobalStateProviderProps> = ({ children }) => {
    const [readArticlesCount, setReadArticlesCount] = useState<number>(0);
    const [personalizedFeedEnabled, setPersonalizedFeedEnabled] = useState<boolean>(true);
    const [userId, setUserId] = useState<string | null>(null);
    const [popularArticles, setPopularArticles] = useState<Article[]>([]);
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);
    const [popularPage, setPopularPage] = useState<number>(0);
    const [personalizedPage, setPersonalizedPage] = useState<number>(0);
    const [archivedPage, setArchivedPage] = useState<number>(0);

    useEffect(() => {
        const storedUserId = localStorage.getItem('user_id');
        setUserId(storedUserId);
    }, []);

    const incrementReadArticlesCount = () => {
        setReadArticlesCount(prevCount => prevCount + 1);
        if (readArticlesCount >= 5) {
            setPersonalizedFeedEnabled(false);
        }
    };

    const updateReadArticlesCount = () => {
        fetch(`http://localhost:8080/api/user/${userId}/read-articles`)
            .then(response => response.json())
            .then(data => {
                setArchivedArticles(data);
                setReadArticlesCount(data.length);

                if (readArticlesCount >= 5) {
                    setPersonalizedFeedEnabled(false);
                }
            });
        if (readArticlesCount >= 5) {
            setPersonalizedFeedEnabled(false);
        }
    };

    const fetchArticles = async (endpoint: string, page: number, setArticles: React.Dispatch<React.SetStateAction<Article[]>>, append: boolean = false) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/articles/${endpoint}?page=${page}&size=20`);
            setArticles(prevArticles => append ? [...prevArticles, ...response.data.content] : response.data.content);
        } catch (error) {
            console.error(`Error fetching ${endpoint} articles:`, error);
        }
    };

    const fetchPopularArticles = (page: number = 0) => fetchArticles('popular', page, setPopularArticles);
    const fetchMorePopularArticles = () => {
        setPopularPage(prevPage => {
            const nextPage = prevPage + 1;
            fetchPopularArticles(nextPage);
            return nextPage;
        });
    };

    const fetchPersonalizedArticles = (page: number = 0) => fetchArticles('personalized', page, setPersonalizedArticles);
    const fetchMorePersonalizedArticles = () => {
        setPersonalizedPage(prevPage => {
            const nextPage = prevPage + 1;
            fetchPersonalizedArticles(nextPage);
            return nextPage;
        });
    };

    const fetchArchivedArticles = (page: number = 0) => fetchArticles('archived', page, setArchivedArticles);
    const fetchMoreArchivedArticles = () => {
        setArchivedPage(prevPage => {
            const nextPage = prevPage + 1;
            fetchArchivedArticles(nextPage);
            return nextPage;
        });
    };

    useEffect(() => {
        fetchPopularArticles();
        fetchPersonalizedArticles();
        fetchArchivedArticles();
    }, []);

    return (
        <GlobalStateContext.Provider value={{
            readArticlesCount,
            incrementReadArticlesCount,
            updateReadArticlesCount,
            personalizedFeedEnabled,
            userId,
            setUserId,
            popularArticles,
            fetchPopularArticles,
            fetchMorePopularArticles,
            personalizedArticles,
            fetchPersonalizedArticles,
            fetchMorePersonalizedArticles,
            archivedArticles,
            fetchArchivedArticles,
            fetchMoreArchivedArticles,
        }}>
            {children}
        </GlobalStateContext.Provider>
    );
};

export { GlobalStateProvider, GlobalStateContext };
