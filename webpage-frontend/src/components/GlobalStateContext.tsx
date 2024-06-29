import React, {createContext, Dispatch, ReactNode, SetStateAction, useEffect, useState} from 'react';
import { Article } from "@/types/article.ts";

interface GlobalStateContextProps {
    readArticlesCount: number;
    incrementReadArticlesCount: () => void;
    updateReadArticlesCount: () => void;
    personalizedFeedEnabled: boolean;

    userId: string | null;
    setUserId: (userId: string) => void;

    personalizedArticles: Article[];
    setPersonalizedArticles: Dispatch<SetStateAction<Article[]>>;

    archivedArticles: Article[];
    setArchivedArticles: Dispatch<SetStateAction<Article[]>>;

    popularArticles: Article[];
    setPopularArticles: Dispatch<SetStateAction<Article[]>>;
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

    return (
        <GlobalStateContext.Provider value={{
            readArticlesCount,
            incrementReadArticlesCount,
            updateReadArticlesCount,
            personalizedFeedEnabled,
            userId,
            setUserId,

            archivedArticles,
            setArchivedArticles,

            personalizedArticles,
            setPersonalizedArticles,

            popularArticles,
            setPopularArticles,
        }}>
            {children}
        </GlobalStateContext.Provider>
    );
};

export { GlobalStateProvider, GlobalStateContext };
