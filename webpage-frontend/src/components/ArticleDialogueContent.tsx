import { DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import './ArticleDialogueContent.css'; // Without it - most tags (like <h1> or <ul>) are restricted from rendering.
import { Article } from "@/types/article";
import axios from 'axios';
import { useEffect, useState } from "react";

type ArticleDialogueContentProps = {
    article: Article;
    isOpen: boolean;
};

const ArticleDialogueContent: React.FC<ArticleDialogueContentProps> = ({ article, isOpen }) => {
    const [summary, setSummary] = useState<string | null>(article.summary);
    const [loading, setLoading] = useState<boolean>(!article.summary);

    useEffect(() => {
        const fetchSummary = async () => {
            if (!article.summary && isOpen) {
                setLoading(true);
                try {
                    console.log("Requesting the summary from chatGPT...")
                    const response = await axios.get<string>(`http://localhost:8080/api/articles/summary/${article.uri}`);
                    setSummary(response.data);
                    article.summary = response.data;
                } catch (error) {
                    console.error('Error fetching summary: ', error);
                } finally {
                    setLoading(false);
                }
            }
        };

        fetchSummary();
    }, [isOpen, article]);

    return (
        <DialogContent className="max-w-4xl sm:w-[90%] md:w-[75%] lg:w-[66%]">
            <DialogHeader>
                <DialogTitle>{article.title}</DialogTitle>
                <DialogDescription>{article.publisher_name}</DialogDescription>
            </DialogHeader>
            {loading ? (
                <p>Summarizing the article...</p>
            ) : (
                <div className="dialogue-content" dangerouslySetInnerHTML={{ __html: summary || '' }} />
            )}
            <DialogFooter className="">
                <div className="flex flex-wrap gap-x-2 gap-y-1 justify-between w-full">
                    <a href={article.url} target="_blank" rel="noopener noreferrer" className="flex-1 min-w-fit w-full md:w-auto">
                        <Button className="w-full">Read the Source</Button>
                    </a>
                    <Button className="flex-1 min-w-fit w-full md:w-auto">More Articles like this</Button>
                    <Button className="flex-1 min-w-fit w-full md:w-auto">Less Articles like this</Button>
                    <Button className="flex-1 min-w-fit w-full md:w-auto">Share</Button>
                </div>
            </DialogFooter>
        </DialogContent>
    );
}

export default ArticleDialogueContent;
