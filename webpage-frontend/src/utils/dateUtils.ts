export const timeSince = (date: string): string => {
    const now = new Date();
    const past = new Date(date);

    // Convert the past date from UTC to Pacific Time Zone
    const pastInPT = new Date(past.toLocaleString('en-US', { timeZone: 'America/Los_Angeles' }));

    const diffInSeconds = Math.floor((pastInPT.getTime() - now.getTime()) / 1000);

    const units: { unit: string; seconds: number }[] = [
        { unit: 'minute', seconds: 60 },
        { unit: 'hour', seconds: 3600 },
        { unit: 'day', seconds: 86400 },
    ];

    for (let i = units.length - 1; i >= 0; i--) {
        const { unit, seconds } = units[i];
        if (diffInSeconds >= seconds) {
            const value = Math.floor(diffInSeconds / seconds);
            return `${value} ${unit}${value > 1 ? 's' : ''} ago`;
        }
    }

    return `${diffInSeconds} second${diffInSeconds !== 1 ? 's' : ''} ago`;
};
