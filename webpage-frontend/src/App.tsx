import Feed, {ArchivedFeed, PersonalizedFeed, PopularFeed} from "@/components/Feed.tsx";
import Header from "@/components/Header.tsx";
import UserIdentifier from "@/components/UserIdentifier.tsx";
import {ThemeProvider} from "@/components/ThemeProvider.tsx";
import {useState} from "react";
import {Article} from "@/types/article.ts";
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom';
function App() {
    const [currentTab, setCurrentTab] = useState<string>('popular');
    const [popularArticles, setPopularArticles] = useState<Article[]>([]);
    const [personalizedArticles, setPersonalizedArticles] = useState<Article[]>([]);
    const [archivedArticles, setArchivedArticles] = useState<Article[]>([]);
    const [userId] = useState<string | null>(localStorage.getItem('essence-news-user-id'));
    const [canAccessPersonalized, setCanAccessPersonalized] = useState<boolean>(false);
    const [articles, setArticles] = useState<Article[]>([]);

    return (
        <div className="p-20 flex justify-center bg-accent">
            <ThemeProvider defaultTheme="light" storageKey="essence-news-ui-theme">
                <UserIdentifier />
                <Header/>
                <Switch>
                    <Route path="/popular">
                        <PopularFeed articles={popularArticles} setArticles={setPopularArticles} />
                    </Route>
                    <Route path="/personalized">
                        <PersonalizedFeed articles={personalizedArticles} setArticles={setPersonalizedArticles} />
                    </Route>
                    <Route path="/archived">
                        <ArchivedFeed articles={archivedArticles} setArticles={setArchivedArticles} />
                    </Route>
                    <Route path="/">
                        <PopularFeed articles={popularArticles} setArticles={setPopularArticles} />
                    </Route>
                </Switch>
            </ThemeProvider>
        </div>
    );
}

export default App;