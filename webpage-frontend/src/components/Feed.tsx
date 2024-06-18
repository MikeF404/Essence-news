import React, {useContext, useEffect} from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { Article } from '@/types/article';
import {GlobalStateContext} from "@/components/GlobalStateContext.tsx";

interface FeedProps {
    endpoint: string;
    articles: Article[];
    setArticles: React.Dispatch<React.SetStateAction<Article[]>>;
}

const Feed: React.FC<FeedProps> = ({ endpoint, articles, setArticles }) => {
    const { readArticlesCount, updateReadArticlesCount, personalizedFeedEnabled, userId, setUserId} = useContext(GlobalStateContext);

    useEffect(() => {
        fetch(endpoint)
            .then(response => response.json())
            .then(data => setArticles(data));
        console.log(articles);
    }, [endpoint, setArticles]);

    return (
        <div className="space-y-2">
            {articles.map(article => (
                <ArticleCard key={article.uri} article={article} />
            ))}
        </div>
    );
};

export default Feed;
