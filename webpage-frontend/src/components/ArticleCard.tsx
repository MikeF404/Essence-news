import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import React from 'react'

export const ArticleCard = React.forwardRef(({ onClick, ...props }, ref) => (
    <Card className="w-[650px] cursor-pointer" onClick={onClick} ref={ref} {...props}>
        <CardHeader>
            <CardTitle>Article Title</CardTitle>
            <CardDescription>Description:</CardDescription>
        </CardHeader>
        <CardContent>
            <form>
                <div className="grid w-full items-center gap-4">
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor="name">Name</Label>
                        <Input id="name" placeholder="Name of your project" />
                    </div>
                    <div className="flex flex-col space-y-1.5">
                        <Label htmlFor="framework">Framework</Label>
                    </div>
                </div>
            </form>
        </CardContent>
        <CardFooter className="flex justify-between">
            <Button variant="outline">Cancel</Button>
            <Button>Deploy</Button>
        </CardFooter>
    </Card>
));

ArticleCard.displayName = 'ArticleCard';