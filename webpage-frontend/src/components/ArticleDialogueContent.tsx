import { DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import './ArticleDialogueContent.css'; // Without it - most tags (like <h1> or <ul>) are restricted from rendering.
import { Article } from "@/types/article";
import axios from 'axios';
import { useEffect, useState } from "react";
import {getURLDomain} from "@/utils/getURLDomain.ts";
import { Skeleton } from "@/components/ui/skeleton"


type ArticleDialogueContentProps = {
    article: Article;
    isOpen: boolean;
};

const ArticleDialogueContent: React.FC<ArticleDialogueContentProps> = ({ article, isOpen }) => {
    const [summary, setSummary] = useState<string | null>(article.summary);
    const [loading, setLoading] = useState<boolean>(!article.summary);

    useEffect(() => {
        const fetchSummary = async () => {
            const userId = localStorage.getItem('user_id');
            if (!article.summary && isOpen) {
                setLoading(true);
                article.viewCount++;
                try {
                    console.log("Requesting the summary from chatGPT...")
                    const response = await axios.get<string>(`http://localhost:8080/api/articles/summary/${article.uri}`, {
                        headers: {
                            userId: userId || '',
                        },
                    });

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
                <DialogDescription>Source: {getURLDomain(article.url)}</DialogDescription>
            </DialogHeader>
            {
                (article.image) &&
                <div className="flex mx-auto w-full h-60 my-[-1em] rounded-xl justify-center">

                    <img
                        className="rounded-xl border-4 "
                        src={article.image}
                        alt="article Image"
                    />
                </div>
                }

            {loading ? (
                <div>
                    <p>[summarizing the article using ChatGPT. It might take about 4 seconds]</p>
                    <div className="flex flex-col space-y-4">
                        <div className="space-y-2">
                            <Skeleton className="h-4 w-[90%]" />
                            <Skeleton className="h-4 w-[76%]" />
                            <Skeleton className="h-4 w-[87%]" />
                            <Skeleton className="h-4 w-[89%]" />
                        </div>
                        <ul className="ml-4 space-x-2 flex flex-row">
                            <div className="mt-1 space-y-4">
                                <Skeleton className="h-2 w-2" />
                                <Skeleton className="h-2 w-2" />
                                <Skeleton className="h-2 w-2" />

                            </div>
                            <div className="space-y-2">
                                <Skeleton className="h-4 w-[250px]" />
                                <Skeleton className="h-4 w-[250px]" />
                                <Skeleton className="h-4 w-[200px]" />

                            </div>

                        </ul>
                        <div className="space-y-2">
                            <Skeleton className="h-4 w-[80%]" />
                            <Skeleton className="h-4 w-[87%]" />
                            <Skeleton className="h-4 w-[60%]" />
                        </div>
                    </div>
                </div>

            ) : (
                <div className="dialogue-content" dangerouslySetInnerHTML={{ __html: summary || '' }} />
            )}
            <DialogFooter className="">

                <div className="flex flex-wrap gap-x-2 gap-y-1 justify-between w-full">
                    <a href={article.url} target="_blank" rel="noopener noreferrer" className="flex-1 min-w-fit w-full md:w-auto">
                        <Button className="w-full">Read the Source</Button>
                    </a>
                    <Button className="flex-1 min-w-fit w-full md:w-auto">Share</Button>
                    <Button className="flex-1 min-w-fit w-full md:w-auto">More like this</Button>
                    <Button className="flex-1 min-w-fit w-full md:w-auto">Less like this</Button>

                </div>
            </DialogFooter>
        </DialogContent>
    );
}

export default ArticleDialogueContent;
