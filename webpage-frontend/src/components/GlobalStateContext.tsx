import React, { createContext, ReactNode, useEffect, useState } from 'react';
import axios from 'axios';
import { Article } from "@/types/article.ts";

interface GlobalStateContextProps {
    readArticlesCount: number;
    incrementReadArticlesCount: () => void;
    updateReadArticlesCount: () => void;
    personalizedFeedEnabled: boolean;
    userId: string | null;
    setUserId: (userId: string) => void;
    popularArticles: Article[];
    hasMorePopular: boolean;
    fetchPopularArticles: (page?: number) => void;
    fetchMorePopularArticles: () => void;
    personalizedArticles: Article[];
    hasMorePersonalized: boolean;
    fetchPersonalizedArticles: (page?: number) => void;
    fetchMorePersonalizedArticles: () => void;
    archivedArticles: Article[];
    hasMoreArchived: boolean;
    fetchArchivedArticles: (page?: number) => void;
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
    const [hasMorePopular, setHasMorePopular] = useState<boolean>(true);
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [hasMorePersonalized, setHasMorePersonalized] = useState<boolean>(true);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);
    const [hasMoreArchived, setHasMoreArchived] = useState<boolean>(true);
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

    const fetchArticles = async (endpoint: string, page: number, setArticles: React.Dispatch<React.SetStateAction<Article[]>>, setHasMore: React.Dispatch<React.SetStateAction<boolean>>, append: boolean = false) => {
        try {
            const response = await axios.get(`http://localhost:8080/api/articles/${endpoint}?page=${page}&size=20`);
            setArticles(prevArticles => {
                const newArticles = response.data.content.filter((newArticle: Article) =>
                    !prevArticles.some((prevArticle: Article) => prevArticle.uri === newArticle.uri)
                );
                return append ? [...prevArticles, ...newArticles] : newArticles;
            });
            setHasMore(response.data.content.length === 20);
        } catch (error) {
            console.error(`Error fetching ${endpoint} articles:`, error);
        }
    };

    const fetchPopularArticles = (page: number = 0) => fetchArticles('popular', page, setPopularArticles, setHasMorePopular);
    const fetchMorePopularArticles = () => {
        setPopularPage(prevPage => {
            const nextPage = prevPage + 1;
            fetchArticles('popular', nextPage, setPopularArticles, setHasMorePopular, true);
            return nextPage;
        });
    };

    const fetchPersonalizedArticles = (page: number = 0) => fetchArticles('personalized', page, setPersonalizedArticles, setHasMorePersonalized);
    const fetchMorePersonalizedArticles = () => {
        setPersonalizedPage(prevPage => {
            const nextPage = prevPage + 1;
            fetchArticles('personalized', nextPage, setPersonalizedArticles, setHasMorePersonalized, true);
            return nextPage;
        });
    };

    const fetchArchivedArticles = (page: number = 0) => fetchArticles('archived', page, setArchivedArticles, setHasMoreArchived);
    const fetchMoreArchivedArticles = () => {
        setArchivedPage(prevPage => {
            const nextPage = prevPage + 1;
            fetchArticles('archived', nextPage, setArchivedArticles, setHasMoreArchived, true);
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
            hasMorePopular,
            fetchPopularArticles,
            fetchMorePopularArticles,
            personalizedArticles,
            hasMorePersonalized,
            fetchPersonalizedArticles,
            fetchMorePersonalizedArticles,
            archivedArticles,
            hasMoreArchived,
            fetchArchivedArticles,
            fetchMoreArchivedArticles,
        }}>
            {children}
        </GlobalStateContext.Provider>
    );
};

export { GlobalStateProvider, GlobalStateContext };
