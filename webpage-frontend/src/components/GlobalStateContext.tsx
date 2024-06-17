import React, { createContext, ReactNode, useEffect, useState } from 'react';

interface GlobalStateContextProps {
    readArticlesCount: number;
    updateReadArticlesCount: (increment: number) => void;
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

        // Fetch initial read articles count from server or localStorage
        if (storedUserId) {
            fetch(`/api/read-articles-count/${storedUserId}`)
                .then(response => response.json())
                .then(data => {
                    setReadArticlesCount(data);
                    if (data >= 5) {
                        setPersonalizedFeedEnabled(false);
                    }
                });
        }
    }, []);

    const updateReadArticlesCount = (increment: number) => {
        const newCount = readArticlesCount + increment;
        setReadArticlesCount(newCount);
        if (newCount >= 5) {
            setPersonalizedFeedEnabled(false);
        }
    };

    return (
        <GlobalStateContext.Provider value={{ readArticlesCount, updateReadArticlesCount, personalizedFeedEnabled, userId, setUserId }}>
            {children}
        </GlobalStateContext.Provider>
    );
};

export { GlobalStateProvider, GlobalStateContext };