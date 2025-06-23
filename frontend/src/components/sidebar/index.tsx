import { Link } from "react-router";
import "./sidebar.css";

type SidebarProviderProps = {
    children: React.ReactNode;
}

function SidebarProvider({ children }: SidebarProviderProps) {
    return (
        <div className="sidebar-provider">
            <Sidebar />
            <main className="sidebar-children">
                {children}
            </main>
        </div>
    )
}

function Sidebar() {
    return (
        <div className="sidebar">
            <div className="title">
                Drive
            </div>
            <button className="create-folder-button">
                + Nuevo
            </button>
            <div className="sections-container">
                <Link to={"/"} className="section">
                    Principal
                </Link>
            </div>
        </div>
    )
}

export {
    SidebarProvider,
    Sidebar
}