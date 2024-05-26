import {ArticleCard} from "@/components/ArticleCard.tsx";
import React, { useEffect, useState } from 'react';
import axios from 'axios';



const Feed = () => {
    const [articles, setArticles] = useState([]);

    useEffect(() => {
        const fetchArticles = async () => {
            try{
                const response = await axios.get('http://localhost:8080/home');
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
                    <ArticleCard key={article.article_id} article={article} />
                ))
            )}

        </div>
    );

};

export default Feed;