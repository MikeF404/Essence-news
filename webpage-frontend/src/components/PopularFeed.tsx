import React, {useContext, useEffect, useState} from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { Article } from '@/types/article';
import {GlobalStateContext} from "@/components/GlobalStateContext.tsx";
import {Button} from "@/components/ui/button.tsx";

const PopularFeed: React.FC = () => {
    const {popularArticles, fetchMorePopularArticles} = useContext(GlobalStateContext);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);

    useEffect(() => {
        fetchMorePopularArticles();
    }, []);

    return (
        <div className="space-y-2">
            {popularArticles.map(article => (
                <ArticleCard key={article.uri} article={article} />
            ))}
            {hasMore && <Button onClick={() => setPage(page + 1)}>Load More</Button>}
        </div>
    );
};

export default PopularFeed;
