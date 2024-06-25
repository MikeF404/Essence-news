import React, {useContext, useEffect, useState} from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { Article } from '@/types/article';
import {GlobalStateContext} from "@/components/GlobalStateContext.tsx";
import axios from "axios";
import {Button} from "@/components/ui/button.tsx";

interface FeedProps {
    endpoint: string;
    articles: Article[];
    setArticles: React.Dispatch<React.SetStateAction<Article[]>>;
}

const Feed: React.FC<FeedProps> = ({ endpoint, articles, setArticles }) => {
    const { readArticlesCount, incrementReadArticlesCount, personalizedFeedEnabled, userId, setUserId} = useContext(GlobalStateContext);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);

    useEffect(() => {
        loadArticles(page);
    }, [page]);

    const loadArticles = async (page: number) => {
        const response = await axios.get(endpoint +`?page=${page}&size=20`);
        if (response.data.content.length > 0) {
            setArticles(prevArticles => [...prevArticles, ...response.data.content]);
            console.log(response.data.content);
        } else {
            setHasMore(false);
        }
    };
    return (
        <div className="space-y-2">
            {articles.map(article => (
                <ArticleCard key={article.uri} article={article} />
            ))}
            {hasMore && <Button onClick={() => setPage(page + 1)}>Load More</Button>}
        </div>
    );
};

export default Feed;
