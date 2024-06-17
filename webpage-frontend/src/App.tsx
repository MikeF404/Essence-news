import React, {useState} from 'react';
import { BrowserRouter as Router} from 'react-router-dom';
import Header from '@/components/Header';
import {GlobalStateProvider} from '@/components/GlobalStateContext';
import {ThemeProvider} from "@/components/ThemeProvider.tsx";
import UserIdentifier from "@/components/UserIdentifier.tsx";
import AppRoutes from "@/components/AppRoutes.tsx";

const App: React.FC = () => {
    const [loading, setLoading] = useState(true);
    return (
        <div className="p-20 flex justify-center bg-accent">
            <ThemeProvider defaultTheme="light" storageKey="essence-news-ui-theme">
                <GlobalStateProvider>
                    <UserIdentifier setLoading={setLoading} />
                    {loading ? (
                        <div>Loading...</div>
                    ) : (
                        <Router>
                            <Header />
                            <AppRoutes />
                        </Router>
                    )}

                </GlobalStateProvider>
            </ThemeProvider>
        </div>
    );
};

export default App;

