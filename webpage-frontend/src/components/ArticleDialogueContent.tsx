
import {DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle} from "@/components/ui/dialog.tsx";
import {Button} from "@/components/ui/button.tsx";
import './ArticleDialogueContent.css'; //Without it - most tags (like <h1> or <ul>) are restricted from rendering.


export default function ArticleDialogueContent() {
    const articleContent = `
<h1>Summary of X TV App Launch</h1>
<p>Elon Musk's social media platform X (formerly Twitter) is preparing to launch its X TV app, which will bring real-time, engaging content to smart TVs. The announcement was made by CEO Linda Yaccarino through a post on the platform.</p>
<h2>Key Highlights:</h2>
<ul>
    <li><strong>X TV App Features:</strong>
        <ul>
            <li>Real-time and immersive content.</li>
            <li>Available on most smart TVs.</li>
            <li>Ability to cast videos from mobile phones to big screens.</li>
        </ul>
    </li>
    <li><strong>Video Content:</strong>
        <ul>
            <li>Thumbnails of videos such as Tucker Carlson's interview with Vladimir Putin and SpaceX rocket launch footage.</li>
            <li>AI-curated video content.</li>
            <li>Dedicated video search and feed.</li>
        </ul>
    </li>
    <li><strong>User Experience:</strong>
        <ul>
            <li>Tracks viewing habits from the X smartphone app.</li>
            <li>Allows continuation of watching videos on the X TV app.</li>
        </ul>
    </li>
</ul>
<p>X aims to compete with YouTube by enabling paid users to upload longer, higher-resolution videos. Free users can upload videos up to 140 seconds, while Premium users can upload 1080p videos up to two hours or 720p videos up to three hours.</p>
<p>Additionally, the platform's flexibility may allow users to upload entire movies, as evidenced by an instance where a user uploaded the entire "Dune: Part Two" film before it was taken down. The X TV app is expected to enhance user engagement by providing a high-quality entertainment experience on larger screens.</p>
`;



    return (
        <DialogContent className="max-w-4xl sm:w-[90%] md:w-[75%] lg:w-[66%]">
            <DialogHeader>
                <DialogTitle>Article Title</DialogTitle>
                <DialogDescription>
                    Publisher
                </DialogDescription>
            </DialogHeader>

            <div className="dialogue-content" dangerouslySetInnerHTML={{ __html: articleContent }} />

        <DialogFooter className="">
            <div className="flex flex-wrap gap-x-2 gap-y-1 justify-between w-full">
                <Button className="flex-1 min-w-fit w-full md:w-auto">Read the Source</Button>
                <Button className="flex-1 min-w-fit w-full md:w-auto">More Articles like this</Button>
                <Button className="flex-1 min-w-fit w-full md:w-auto">Less Articles like this</Button>
                <Button className="flex-1 min-w-fit w-full md:w-auto">Share</Button>
            </div>


        </DialogFooter>

        </DialogContent>
    );

}
