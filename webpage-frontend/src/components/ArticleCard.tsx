import {
    Card,
} from "@/components/ui/card";

import React, { useState } from 'react';
import { Dialog, DialogTrigger} from "@/components/ui/dialog";
import ArticleDialogueContent from "@/components/ArticleDialogueContent";
import { Article } from "@/types/article";
import {timeSince} from "@/utils/dateUtils.ts";

type ArticleCardProps = {
    article: Article;
} & React.ComponentPropsWithoutRef<'div'>;

export const ArticleCard = React.forwardRef<HTMLDivElement, ArticleCardProps>(
    ({ article, ...props }, ref) => {
        const [isOpen, setIsOpen] = useState(false);

        return (
            <Dialog open={isOpen} onOpenChange={setIsOpen}>
                <DialogTrigger asChild>
                    <Card
                        className="cursor-pointer"
                        onClick={() => setIsOpen(true)}
                        ref={ref}
                        {...props}
                    >
                        <div className="flex justify-between pr-3 pl-3 items-center">
                            <div>
                                <p className="text-xl font-bold">
                                    {article.title}
                                </p>
                                <p className="text-foreground">
                                    {timeSince(article.dateTimePub)}
                                </p>
                            </div>
                            <div>
                                <img
                                    className="mt-3 mb-3 max-h-16 rounded-xl"
                                    src={article.image}
                                    alt="article Image"
                                />
                            </div>
                        </div>
                    </Card>
                </DialogTrigger>
                <ArticleDialogueContent key={article.uri} article={article} isOpen={isOpen} />
            </Dialog>
        );
    }
);

ArticleCard.displayName = 'ArticleCard';
