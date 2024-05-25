
import {
    Card,
} from "@/components/ui/card"

import React from 'react'
import {Dialog, DialogTrigger} from "@/components/ui/dialog.tsx";
import ArticleDialogueContent from "@/components/ArticleDialogueContent.tsx";


type ArticleCardProps = {
    onClick?: React.MouseEventHandler<HTMLDivElement>;

} & React.ComponentPropsWithoutRef<'div'>;

export const ArticleCard = React.forwardRef<HTMLDivElement, ArticleCardProps>(
    ({ onClick, ...props }, ref) => (
        <Dialog>
            <DialogTrigger asChild>
                <Card className="cursor-pointer" onClick={onClick} ref={ref} {...props}>
                    <div className="flex justify-between pr-3 pl-3 items-center">
                        <div className="">

                                <p className="text-xl font-bold">
                                    Article Title
                                </p>
                                <p className="text-foreground">
                                    Publisher
                                </p>


                        </div>
                        <div className="">
                            <img
                                className="mt-3 max-h-16 rounded-xl"
                                src="https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RE4wwut?ver=b2fa"
                                alt="article Image"
                            />
                        </div>
                    </div>
                    <p className="p-2 pt-0">
                        Short Description: labore et dolore magna aliqua Ut enim ad minim veniam, quis nostrud exercitation ullamco Laboris nisi ut aliquip ex a commodo consequat.
                    </p>
                   </Card>
            </DialogTrigger>
            <ArticleDialogueContent/>
        </Dialog>

));

ArticleCard.displayName = 'ArticleCard';