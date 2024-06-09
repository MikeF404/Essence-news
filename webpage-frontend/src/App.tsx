import Feed from "@/components/Feed.tsx";
import Header from "@/components/Header.tsx";
import UserIdentifier from "@/components/UserIdentifier.tsx";
import {ThemeProvider} from "@/components/ThemeProvider.tsx";
function App() {


  return (
    <div className="p-20 flex justify-center bg-accent">
        <ThemeProvider defaultTheme="light" storageKey="vite-ui-theme">
            <UserIdentifier />
            <Header />
            <Feed/>
        </ThemeProvider>
        
    </div>
  )
}

export default App
