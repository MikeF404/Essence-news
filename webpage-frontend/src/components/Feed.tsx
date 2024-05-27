import {ArticleCard} from "@/components/ArticleCard.tsx";
import React, { useEffect, useState } from 'react';
import axios from 'axios';



const Feed = () => {
    const [articles, setArticles] = useState([]);
    const testArticles = [
        {
            id: 1,
            title: "Article 1",
            publisher_name: "Publisher Name",
            description: "Description for Article 1",
            content: "Content of the first test article.",
        },
        {
            id: 2,
            title: "Article 2",
            publisher_name: "Publisher Name",
            description: "Description for Article 2",
            content: "Content of the second test article.",
        },
        {
            id: 3,
            title: "Article 3",
            publisher_name: "Publisher Name",
            description: "Description for Article 3",
            content: "Content of the third test article.",
        },
        {
            id: 4,
            title: "Article 1",
            publisher_name: "Publisher Name",
            description: "Description for Article 1",
            content: "Content of the first test article.",
        },
        {
            id: 5,
            title: "Article 2",
            publisher_name: "Publisher Name",
            description: "Description for Article 2",
            content: "Content of the second test article.",
        },
        {
            id: 6,
            title: "Article 3",
            publisher_name: "Publisher Name",
            description: "Description for Article 3",
            content: "Content of the third test article.",
        },
        {
            id: 7,
            title: "Article 1",
            publisher_name: "Publisher Name",
            description: "Description for Article 1",
            content: "Content of the first test article.",
        },
        {
            id: 8,
            title: "Article 2",
            publisher_name: "Publisher Name",
            description: "Description for Article 2",
            content: "Content of the second test article.",
        },
        {
            id: 9,
            title: "Article 3",
            publisher_name: "Publisher Name",
            description: "Description for Article 3",
            content: "Content of the third test article.",
        },
        {
            id: 10,
            title: "Article 1",
            publisher_name: "Publisher Name",
            description: "Description for Article 1",
            content: "Content of the first test article.",
        },
        {
            id: 11,
            title: "Article 2",
            publisher_name: "Publisher Name",
            description: "Description for Article 2",
            content: "Content of the second test article.",
        },
        {
            id: 12,
            title: "Article 3",
            publisher_name: "Publisher Name",
            description: "Description for Article 3Content of the third test article. Content of the third test article. Content of the third test article.",
            content: "Content of the third test article. ",
        },
    ];


    useEffect(() => {
        const fetchArticles = async () => {
            try{
                const response = await axios.get('http://localhost:8080/home');
                setArticles(response.data);
                console.log(response.data);
            } catch (error){
                console.error('Error fetching articles: ', error);

                setArticles(testArticles);

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
                    <ArticleCard key={article.id} article={article} />
                ))
            )}

        </div>
    );

};

export default Feed;