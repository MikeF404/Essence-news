export function isValidImageUrl(url: string) {
    const img = new Image();
    img.src = url;
    return img.complete || (img.width + img.height) > 0;
}