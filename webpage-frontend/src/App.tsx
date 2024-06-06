import Feed from "@/components/Feed.tsx";
import Header from "@/components/Header.tsx";
import UserIdentifier from "@/components/UserIdentifier.tsx";
function App() {


  return (
    <div className="p-20 flex justify-center bg-accent">
        <UserIdentifier />
        <Header />
        <Feed/>
    </div>
  )
}

export default App
