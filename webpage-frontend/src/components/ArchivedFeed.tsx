import React, {useContext, useCallback, useEffect} from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { GlobalStateContext } from "@/components/GlobalStateContext.tsx";
import axios from "axios";

const ArchivedFeed: React.FC = () => {
    const context = useContext(GlobalStateContext);
    if (!context) {
        throw new Error("GlobalStateContext must be used within a GlobalStateProvider");
    }
    const { archivedArticles, setArchivedArticles, userId} = context;

    useEffect(() => {
        if (!archivedArticles || archivedArticles.length === 0){
            loadArticles();
        }
    }, []);

    const loadArticles = useCallback(async () => {
        try {
            //TODO: add pagination for the archived articles?
            const response = await axios.get(`http://localhost:8080/api/user/${userId}/read-articles`);
            console.log(response);
            setArchivedArticles((response.data));
        } catch (error) {
            console.error('Error loading articles:', error);
        }
    }, [setArchivedArticles]);

    return (
        <div className="space-y-2">
            {archivedArticles.map((article) => (
                <ArticleCard
                    key={article.uri}
                    article={article}
                />
            ))}
        </div>
    );
};

export default ArchivedFeed;
