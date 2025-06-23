import { useContext } from "react";
import type { UploadedFile } from "../../types";
import X from "../svg/x";
import "./styles.css";
import { DriveContext } from "../../context/DriveContext";
import axios from "axios";

function readableFileSize(size: number) {
    var units = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    var i = 0;
    while (size >= 1024) {
        size /= 1024;
        ++i;
    }
    return size.toFixed(1) + ' ' + units[i];
}

export default function UploadedFile({ uploadedFile }: { uploadedFile: UploadedFile }) {
    const [files, setFiles] = useContext(DriveContext);

        console.log(uploadedFile.id);


    const deleteFile = async (e: React.MouseEvent) => {
        e.preventDefault();
        e.stopPropagation();

        const res = await axios.delete("http://localhost:8000/api/v1/files/" + uploadedFile.id);

        if (res.status != 200)
            return;

        const filesCopy = [...files].filter(file => file.id != uploadedFile.id);
        setFiles(filesCopy);
    }

    const downloadFile = async (e: React.MouseEvent) => {
        e.stopPropagation();
        e.preventDefault();

        const response = await axios({
            url: "http://localhost:8000/api/v1/files/" + uploadedFile.id,
            method: 'GET',
            responseType: 'blob'
        });
        if (response.status == 200) {
            const href = URL.createObjectURL(response.data);
            const link = document.createElement('a');
            link.href = href;

            link.setAttribute('download', uploadedFile.fileName);
            document.body.appendChild(link);
            link.click();

            document.body.removeChild(link);
            URL.revokeObjectURL(href);
        }
    }

    return (
        <button className="uploaded-file" onClick={downloadFile}>
            <div className="file-info">
                <span>
                    {uploadedFile.fileName}
                </span>
                <span className="file-size">
                    {readableFileSize(uploadedFile.fileSize)}
                </span>
            </div>
            <div className="delete-file-btn" onClick={deleteFile}>
                <X />
            </div>
        </button>
    )
}