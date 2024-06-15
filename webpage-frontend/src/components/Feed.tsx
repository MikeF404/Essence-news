import {ArticleCard} from "@/components/ArticleCard.tsx";
import {useEffect} from "react";

const PopularFeed = ({ articles, setArticles }) => {
    useEffect(() => {
        if (articles.length === 0) {
            // Fetch articles only if they are not already fetched
            fetch('/api/popular-articles')
                .then(response => response.json())
                .then(data => setArticles(data));
        }
    }, [articles, setArticles]);

    return (
        <div className="space-y-2">
            {articles.map(article => (
                <ArticleCard key={article.id} {...article} />
            ))}
        </div>
    );
};

const PersonalizedFeed = ({ articles, setArticles }) => {
    useEffect(() => {
        if (articles.length === 0) {
            // Fetch articles only if they are not already fetched
            fetch('/api/personalized-articles')
                .then(response => response.json())
                .then(data => setArticles(data));
        }
    }, [articles, setArticles]);

    return (
        <div className="space-y-2">
            {articles.map(article => (
                <ArticleCard key={article.id} {...article} />
            ))}
        </div>
    );
};

const ArchivedFeed = ({ articles, setArticles }) => {
    useEffect(() => {
        if (articles.length === 0) {
            // Fetch articles only if they are not already fetched
            fetch('/api/archived-articles')
                .then(response => response.json())
                .then(data => setArticles(data));
        }
    }, [articles, setArticles]);

    return (
        <div className="space-y-2">
            {articles.map(article => (
                <ArticleCard key={article.id} {...article} />
            ))}
        </div>
    );
};

export { PopularFeed, PersonalizedFeed, ArchivedFeed };
