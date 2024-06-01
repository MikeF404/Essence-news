export const getURLDomain = (url: string): string => {
    const match = url.match(/^https?:\/\/([^/]+)\/?.*$/);
    return match ? match[1] : '';
}

