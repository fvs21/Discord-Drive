import { useContext, useState } from "react";
import "./styles.css";
import axios from "axios";
import { DriveContext } from "../../context/DriveContext";

export default function DriveContainer({ children }: { children: React.ReactNode }) {
    const [hovered, setHovered] = useState(false);
    const [, setFiles] = useContext(DriveContext);

    const drop = (e: React.DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        e.stopPropagation();
        setHovered(false);

        const file = e.dataTransfer.files[0];
        
        if(!file) {
            return;
        }

        submitFile(file);
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
        <div 
            className={`drive-container ${hovered ? "drive-hover" : ""}`}
            onDragEnter={() => setHovered(true)}
            onDragLeave={() => setHovered(false)}
            onDrop={drop}
            onDragOver={(e) => e.preventDefault()}
        >
            <div className="uploaded-files">
                {children}
            </div>
        </div>
    )
}