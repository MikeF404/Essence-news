import React, { useEffect } from 'react';
import axios from 'axios';


const UserIdentifier: React.FC = () => {
    useEffect(() => {
        const getUserId = async (): Promise<string> => {
            let userId = localStorage.getItem('user_id');
            if (!userId) {
                try {
                    const response = await axios.post('http://localhost:8080/api/user/create');
                    userId = response.data.toString();
                    if (userId) {
                        localStorage.setItem('user_id', userId);
                    }
                } catch (error) {
                    console.error('Error generating user ID:', error);
                    return 'Error generating user ID';
                }
            }
            return userId || 'Error generating user ID';
        };

        const fetchUserId = async () => {
            const userId = await getUserId();
            console.log(`User ID: ${userId}`);
        };

        fetchUserId();
    }, []);

    return null;
};

export default UserIdentifier;