import React, { useEffect } from 'react';
import { v4 as uuidv4 } from "uuid";

const UserIdentifier: React.FC = () => {
    useEffect(() => {
        const getUserId = (): string => {
            let userId = localStorage.getItem('user_id');
            if (!userId) {
                userId = uuidv4();
                if (userId != undefined) localStorage.setItem('user_id', userId);
            }
            if (userId != undefined) return userId;
            return "Error generating uuid"
        };

        const userId = getUserId();
        console.log(`User ID: ${userId}`);
    }, []);

    return null;
};

export default UserIdentifier;
