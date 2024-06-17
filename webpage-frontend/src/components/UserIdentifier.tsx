import React, {useContext, useEffect} from 'react';
import axios from 'axios';
import { GlobalStateContext } from '@/components/GlobalStateContext';


const UserIdentifier: React.FC<{ setLoading: (loading: boolean) => void }> = ({ setLoading }) => {
    const { setUserId } = useContext(GlobalStateContext)!;

    useEffect(() => {
        const getUserId = async (): Promise<string> => {
            let userId = localStorage.getItem('essence-news-user-id');
            if (!userId) {
                try {
                    const response = await axios.post('http://localhost:8080/api/user/create');
                    userId = response.data.toString();
                    if (userId) {
                        localStorage.setItem('essence-news-user-id', userId);

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
            setUserId(userId);
            setLoading(false);
        };

        fetchUserId();
    }, [setUserId, setLoading]);

    return null;
};

export default UserIdentifier;