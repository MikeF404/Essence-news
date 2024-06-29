import React from 'react';
import { Route, Routes } from 'react-router-dom';
import PopularFeed from "@/components/PopularFeed.tsx";
import PersonalizedFeed from "@/components/PersonalizedFeed.tsx";
import ArchivedFeed from "@/components/ArchivedFeed.tsx";

const AppRoutes: React.FC = () => {

    return (
        <Routes>
            <Route path="/popular" element={<PopularFeed/>} />
            <Route path="/personalized" element={<PersonalizedFeed/>} />
            <Route path="/archived" element={<ArchivedFeed/>} />
            <Route path="/" element={<PopularFeed/>} />
        </Routes>
    );
};

export default AppRoutes;
