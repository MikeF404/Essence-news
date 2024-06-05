import {
    Card,
} from "@/components/ui/card";

import React, { useState } from 'react';
import { Dialog, DialogTrigger} from "@/components/ui/dialog";
import ArticleDialogueContent from "@/components/ArticleDialogueContent";
import { Article } from "@/types/article";
import {timeSince} from "@/utils/dateUtils.ts";
import {getURLDomain} from "@/utils/getURLDomain.ts";

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
                        <div className="flex justify-between items-center ">
                            <div className="px-3 ">
                                <p className="text-xl font-bold">
                                    {article.title}
                                </p>
                                <p className="text-foreground">
                                    {getURLDomain(article.url)}
                                </p>
                                <p className="text-foreground">
                                    {timeSince(article.dateTimePub)}
                                </p>
                            </div>
                            <div className="">
                                {
                                    (article.image) &&
                                        <img
                                            className="max-w-44 max-h-32 rounded-xl"
                                            src={article.image}
                                            alt="article Image"
                                        />

                                }

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
