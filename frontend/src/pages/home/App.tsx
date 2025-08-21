import './App.css'
import DriveContainer from '../../components/drive-container'
import { SidebarProvider } from '../../components/sidebar'
import { useContext, useEffect, useState } from 'react'
import { DriveContext } from '../../context/DriveContext'
import axios from 'axios'
import UploadedFile from '../../components/uploaded-file'

function App() {
  const [files, setFiles] = useContext(DriveContext);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const getFiles = async () => {
      const res = await axios.get("http://localhost:8000/api/v1/files");
      setFiles(res.data);
      setLoading(false);
    }

    getFiles();
  }, [setFiles]);

  if(loading)
    return <></>;

  return (
    <SidebarProvider>
      <DriveContainer>
        {files.map((file) => (
          <UploadedFile uploadedFile={file} key={file.id}/>
        ))}
      </DriveContainer>
    </SidebarProvider>
  )
}

export default App;