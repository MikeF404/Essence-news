import { useState, useEffect } from 'react';
import logo from "../assets/essence_logo.png";

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
        <header className={`fixed top-0 w-full transition-transform duration-300 ${showHeader ? 'translate-y-0' : '-translate-y-full'}`}>
            <div className="bg-accent-foreground text-background font-bold p-2 text-3xl flex items-center italic">
                <img className="w-12 mr-3" src={logo} alt="logo"/>
                <h1>Essence News</h1>
            </div>
        </header>
    );
};

export default Header;
