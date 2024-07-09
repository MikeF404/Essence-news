import React, {useContext, useRef, useCallback, useEffect, useState} from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { GlobalStateContext} from "@/components/GlobalStateContext.tsx";
import axios from "axios";
import {Article} from "@/types/article.ts";

const PopularFeed: React.FC = () => {
    const context = useContext(GlobalStateContext);
    if (!context) {
        throw new Error("GlobalStateContext must be used within a GlobalStateProvider");
    }
    const { popularArticles, setPopularArticles, updateReadArticlesCount} =
      context;

    let page = 0;
    const PAGE_SIZE = 20;
    const [hasMore, setHasMore] = useState(true);
    const observer = useRef<IntersectionObserver | null>(null);

    useEffect(() => {
        
        if (!popularArticles || popularArticles.length === 0){
            updateReadArticlesCount();
            loadArticles();
        }
    }, []);

    const lastArticleRef = useCallback((node: HTMLElement | null) => {
        if (observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting && hasMore) {
                loadArticles();
            }
        });
        if (node) observer.current.observe(node);
    }, [hasMore]);


    const loadArticles = useCallback(async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/articles/popular?page=${page}&size=${PAGE_SIZE}`);
            if (response.data.content.length === PAGE_SIZE ){
                setPopularArticles(prevArticles => {
                    const newArticles = response.data.content.filter(
                        (article: Article) => !prevArticles.some(prev => prev.uri === article.uri)
                    );
                    return [...prevArticles, ...newArticles];
                });
                console.log("loading page " + page);
                page++;
            } else {
                setHasMore(false);
            }
        } catch (error) {
            console.error('Error loading articles:', error);
        }
    }, [setPopularArticles]);

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
