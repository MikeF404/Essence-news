import {ArticleCard} from "@/components/ArticleCard.tsx";
import { useEffect, useState } from 'react';
import axios from 'axios';
import { Article } from '../types/article';

const Feed = () => {
    const [articles, setArticles] = useState<Article[]>([]);


    useEffect(() => {
        const fetchArticles = async () => {
            try{
                const userId = localStorage.getItem('user_id') || '0';
                const response = await axios.get<Article[]>('http://localhost:8080/api/articles/popular', {
                    headers: {
                        'User-ID': userId
                    }
                });
                setArticles(response.data);
                console.log(response.data);
            } catch (error){
                console.error('Error fetching articles: ', error);
            }
        }

        fetchArticles();
    }, []);

    return(
        <div className="space-y-2">
            {articles.length === 0 ? (
                <p>Loading articles...</p>
            ) : (
                articles.map(article => (
                    <ArticleCard key={article.uri} article={article} />
                ))
            )}

        </div>
    );

};

export default Feed;