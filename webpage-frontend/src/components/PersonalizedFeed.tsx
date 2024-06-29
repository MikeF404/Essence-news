import React, {useContext, useRef, useCallback, useEffect, useState} from 'react';
import { ArticleCard } from '@/components/ArticleCard';
import { GlobalStateContext } from "@/components/GlobalStateContext.tsx";
import axios from "axios";
import {Article} from "@/types/article.ts";

const PersonalizedFeed: React.FC = () => {
    const context = useContext(GlobalStateContext);
    if (!context) {
        throw new Error("GlobalStateContext must be used within a GlobalStateProvider");
    }
    const { personalizedArticles, setPersonalizedArticles, userId} = context;

    let page = 0;
    const PAGE_SIZE = 20;
    const [hasMore, setHasMore] = useState(true);
    const observer = useRef<IntersectionObserver | null>(null);

    useEffect(() => {
        if (!personalizedArticles || personalizedArticles.length === 0){
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
            const response = await axios.get(`http://localhost:8080/api/articles/relevant/${userId}?page${page}&size=${PAGE_SIZE}`);
            console.log(response);
            if (response.data.length === PAGE_SIZE ){
                setPersonalizedArticles(prevArticles => {
                    const newArticles = response.data.filter(
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
    }, [setPersonalizedArticles]);

    return (
        <div className="space-y-2">
            {personalizedArticles.map((article, index) => (
                <ArticleCard
                    key={article.uri}
                    article={article}
                    ref={index === personalizedArticles.length - 1 ? lastArticleRef : null}
                />
            ))}
        </div>
    );
};

export default PersonalizedFeed;
