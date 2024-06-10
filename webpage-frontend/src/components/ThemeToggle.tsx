import {Moon, Sun, SunMoon} from "lucide-react"


import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { useTheme } from "@/components/ThemeProvider"

export function ThemeToggle() {
    const { setTheme } = useTheme()

    return (
        <DropdownMenu >
            <DropdownMenuTrigger asChild>
                <div className="flex cursor-pointer text-accent items-center justify-center">
                    <div className="pr-2">Theme: </div>
                    <Sun className="h-[1.4rem] w-[1.4rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0" />
                    <Moon className="absolute mr-[-3.8rem] h-[1.4rem] w-[1.4rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100" />
                    <span className="sr-only">Toggle theme</span>

                </div>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={() => setTheme("light")}>
                    <div className="flex gap-2"><Sun/>Light</div>

                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("dark")}>
                    <div className="flex gap-2"> <Moon/> Dark </div>
                </DropdownMenuItem>
                <DropdownMenuItem onClick={() => setTheme("system")}>
                    <div className="flex gap-2"> <SunMoon/>System </div>
                </DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
    )
}
