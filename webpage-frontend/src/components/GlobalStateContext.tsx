import React, { createContext, ReactNode, useEffect, useState } from 'react';

interface GlobalStateContextProps {
    readArticlesCount: number;
    incrementReadArticlesCount: () => void;
    updateReadArticlesCount: () => void;
    personalizedFeedEnabled: boolean;
    userId: string | null;
    setUserId: (userId: string) => void;
}

const GlobalStateContext = createContext<GlobalStateContextProps | undefined>(undefined);

interface GlobalStateProviderProps {
    children: ReactNode;
}

const GlobalStateProvider: React.FC<GlobalStateProviderProps> = ({ children }) => {
    const [readArticlesCount, setReadArticlesCount] = useState<number>(0);
    const [personalizedFeedEnabled, setPersonalizedFeedEnabled] = useState<boolean>(true);
    const [userId, setUserId] = useState<string | null>(null);

    useEffect(() => {
        const storedUserId = localStorage.getItem('user_id');
        setUserId(storedUserId);
    }, []);

    const incrementReadArticlesCount = () => {
        setReadArticlesCount(readArticlesCount + 1);
        if (readArticlesCount >= 5) {
            setPersonalizedFeedEnabled(false);
        }

    };

    const updateReadArticlesCount = () => {
        fetch(`http://localhost:8080/api/user/${userId}/read-articles`)
            .then(response => response.json())
            .then(data => {
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
        <GlobalStateContext.Provider value={{ readArticlesCount, incrementReadArticlesCount: incrementReadArticlesCount, updateReadArticlesCount, personalizedFeedEnabled, userId, setUserId }}>
            {children}
        </GlobalStateContext.Provider>
    );
};

export { GlobalStateProvider, GlobalStateContext };