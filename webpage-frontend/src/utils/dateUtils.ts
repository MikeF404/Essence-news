export const timeSince = (date: string): string => {
    const now = new Date();

    const postDate = new Date(date);

    // Convert the past date from UTC to Pacific Time Zone


    const diffInSeconds = Math.floor((now.getTime() - postDate.getTime() +25200000) / 1000);

    const units: { unit: string; seconds: number }[] = [
        { unit: 'minute', seconds: 60 },
        { unit: 'hour', seconds: 3600 },
        { unit: 'day', seconds: 86400 },
    ];

    if (diffInSeconds < units[2].seconds) { // Less than a day
        const hours = Math.floor(diffInSeconds / units[1].seconds);
        const minutes = Math.floor((diffInSeconds % units[1].seconds) / units[0].seconds);
        let result = '';

        if (hours > 0) {
            result += `${hours} hour${hours !== 1 ? 's' : ''} `;
        }

        result += `${minutes} minute${minutes !== 1 ? 's' : ''} ago`;

        return result.trim();
    }

    for (let i = units.length - 1; i >= 0; i--) {
        const { unit, seconds } = units[i];
        if (diffInSeconds >= seconds) {
            const value = Math.floor(diffInSeconds / seconds);
            return `${value} ${unit}${value > 1 ? 's' : ''} ago`;
        }
    }

    return `${diffInSeconds} second${diffInSeconds !== 1 ? 's' : ''} ago`;

};
