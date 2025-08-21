import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './pages/home/App.tsx'
import { BrowserRouter, Route, Routes } from 'react-router'
import Recents from './pages/recents/Recents.tsx'
import DriveContextProvider from './context/DriveContext.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path='/' element={
          <DriveContextProvider>
            <App />
          </DriveContextProvider>
        } />
        <Route path='/recents' element={<Recents />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
