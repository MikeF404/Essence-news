import { useState, useEffect } from 'react';
import logo from "../assets/essence_logo.png";
import {Button} from "@/components/ui/button.tsx";
import {ThemeToggle} from "@/components/ThemeToggle.tsx";


const Header = () => {
    const [showHeader, setShowHeader] = useState(true);
    const [lastScrollY, setLastScrollY] = useState(0);

    const handleScroll = () => {
        const currentScrollY = window.scrollY;

        if (currentScrollY > lastScrollY) {
            // Scrolling down
            setShowHeader(false);
        } else {
            // Scrolling up
            setShowHeader(true);
        }

        setLastScrollY(currentScrollY);
    };

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);

        return () => {
            window.removeEventListener('scroll', handleScroll);
        };
    }, [lastScrollY]);

    return (
        <header className={`fixed top-0 w-screen transition-transform duration-300 ${showHeader ? 'translate-y-0' : '-translate-y-full'}`}>
            <div className="bg-accent-foreground text-accent  p-2 px-4 flex items-center justify-between">
                <div className="flex font-bold italic text-3xl gap-3">
                    <img className="w-12 " src={logo} alt="logo"/>
                    <h1>Essence News</h1>

                </div>
                <div className="gap-2 flex items-center">
                    <div className="pr-3">

                        <ThemeToggle/>
                    </div>
                    <Button variant="outline" className="border-2 border-accent bg-accent-foreground">
                        Popular
                    </Button>
                    <Button variant="outline" className="border-2 border-accent bg-accent-foreground">
                        Personalized
                    </Button>

                </div>


            </div>
        </header>
    );
};

export default Header;
