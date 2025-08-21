import { Link } from "react-router";
import "./sidebar.css";
import { useContext, useRef } from "react";
import axios from "axios";
import { DriveContext } from "../../context/DriveContext";

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
    const inputRef = useRef<HTMLInputElement>(null);
    const [, setFiles] = useContext(DriveContext);

    const clickInput = () => {
        if(!inputRef.current)
            return;

        inputRef.current.click();
    }   

    const submitFile = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);

        const res = await axios.post("http://localhost:8000/api/v1/files", formData);

        if(res.status == 201) {
            setFiles((prev) => ([
                res.data, ...prev
            ]));
        }
    }

    return (
        <div className="sidebar">
            <div className="title">
                Drive
            </div>
            <button className="create-folder-button" onClick={clickInput}>
                + Nuevo
            </button>
            <input ref={inputRef} type="file" hidden onChange={(e) => {
                const file = e.target.files;

                if(file && file[0]) {
                    submitFile(file[0]);
                }
                
            }}/>
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