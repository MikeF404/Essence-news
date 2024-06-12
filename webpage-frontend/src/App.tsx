import Feed from "@/components/Feed.tsx";
import Header from "@/components/Header.tsx";
import UserIdentifier from "@/components/UserIdentifier.tsx";
import {ThemeProvider} from "@/components/ThemeProvider.tsx";
import {useEffect, useState} from "react";
import {Article} from "@/types/article.ts";
import axios from "axios";
function App() {
    const [currentTab, setCurrentTab] = useState<string>('popular');
    const [popularArticles, setPopularArticles] = useState<Article[]>([]);
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);
    const [userId] = useState<string | null>(localStorage.getItem('user_id'));
    const [canAccessPersonalized, setCanAccessPersonalized] = useState<boolean>(false);
    const [articles, setArticles] = useState<Article[]>([]);

    useEffect(() => {
        const fetchPopularArticles = async () => {
            const response = await axios.get('http://localhost:8080/api/articles/popular');
            console.log(response.data);
            setPopularArticles(response.data);
        };

        const fetchPersonalizedArticles = async () => {
            if (userId) {
                const response = await axios.get(`http://localhost:8080/api/articles/relevant/${userId}/0`);
                setPersonalizedArticles(response.data);
            }
        };

        const fetchArchivedArticles = async () => {
            if (userId) {
                const response = await axios.get(`http://localhost:8080/api/user/${userId}/read-articles`);
                console.log(response.data);
                setArchivedArticles(response.data);
                if (response.data.length >= 5) setCanAccessPersonalized(true);
            }
        };

        fetchPopularArticles();
        fetchArchivedArticles();
        console.log(archivedArticles.length);
        if (canAccessPersonalized) {
            fetchPersonalizedArticles();
        }
    }, [userId, canAccessPersonalized]);

    useEffect(() => {
        switch (currentTab) {
            case 'popular':
                setArticles(popularArticles);
                break;
            case 'personalized':
                setArticles(personalizedArticles);
                break;
            case 'archived':
                setArticles(archivedArticles);
                break;
            default:
                setArticles([]);
                break;
        }
    }, [currentTab, popularArticles, personalizedArticles, archivedArticles]);

    return (
        <div className="p-20 flex justify-center bg-accent">
            <ThemeProvider defaultTheme="light" storageKey="vite-ui-theme">
                <UserIdentifier />
                <Header currentTab={currentTab} setCurrentTab={setCurrentTab} />
                <Feed articles={articles} />
            </ThemeProvider>
        </div>
    );
}

export default App;