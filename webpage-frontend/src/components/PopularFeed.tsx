import React, { useContext, useRef, useCallback } from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { GlobalStateContext } from "@/components/GlobalStateContext.tsx";

const PopularFeed: React.FC = () => {
    const { popularArticles, hasMorePopular, fetchMorePopularArticles } = useContext(GlobalStateContext);

    const observer = useRef<IntersectionObserver | null>(null);
    const lastArticleRef = useCallback((node: HTMLElement | null) => {
        if (observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting && hasMorePopular) {
                fetchMorePopularArticles();
            }
        });
        if (node) observer.current.observe(node);
    }, [fetchMorePopularArticles, hasMorePopular]);

    return (
        <div className="space-y-2">
            {popularArticles.map((article, index) => (
                <ArticleCard
                    key={article.uri}
                    article={article}
                    ref={index === popularArticles.length - 1 ? lastArticleRef : null}
                />
            ))}
        </div>
    );
};

export default PopularFeed;
