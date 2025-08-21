import { createContext, useState } from "react";
import type { UploadedFile } from "../types";

export const DriveContext = createContext<[Array<UploadedFile>, React.Dispatch<React.SetStateAction<UploadedFile[]>>]>([[], () => {}]);

export default function DriveContextProvider({ children }: { children: React.ReactNode }) {
    const [files, setFiles] = useState<UploadedFile[]>([]);

    return (
        <DriveContext.Provider value={[files, setFiles]}>
            {children}
        </DriveContext.Provider>
    )
}