
import {
    Card,
} from "@/components/ui/card"

import React from 'react'
import {Dialog, DialogTrigger} from "@/components/ui/dialog.tsx";
import ArticleDialogueContent from "@/components/ArticleDialogueContent.tsx";

type Article = {
    title: string;
    publisher_name: string;
    src_link: string;
    description: string;
    image_url: string;
};

type ArticleCardProps = {
    article: Article;
    onClick?: React.MouseEventHandler<HTMLDivElement>;
} & React.ComponentPropsWithoutRef<'div'>;

export const ArticleCard = React.forwardRef<HTMLDivElement, ArticleCardProps>(
    ({ article, onClick, ...props }, ref) => (
        <Dialog>
            <DialogTrigger asChild>
                <Card className="cursor-pointer" onClick={onClick} ref={ref} {...props}>
                    <div className="flex justify-between pr-3 pl-3 items-center">
                        <div className="">

                                <p className="text-xl font-bold">
                                    {article.title}
                                </p>
                                <p className="text-foreground">
                                    {article.publisher_name}
                                </p>


                        </div>
                        <div className="">
                            <img
                                className="mt-3 max-h-16 rounded-xl"
                                src={article.image_url}
                                alt="article Image"
                            />
                        </div>
                    </div>
                    <p className="p-2 pt-0">
                        {article.description}
                    </p>
                   </Card>
            </DialogTrigger>
            <ArticleDialogueContent/>
        </Dialog>

));

ArticleCard.displayName = 'ArticleCard';