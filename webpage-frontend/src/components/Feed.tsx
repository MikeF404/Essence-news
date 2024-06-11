import {ArticleCard} from "@/components/ArticleCard.tsx";
import { Article } from '../types/article';

type FeedProps = {
    articles: Article[];
};

const Feed: React.FC<FeedProps> = ({ articles }) => {

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