import { Space } from "antd";
import "./App.css";
import AppHeader from "./Components/AppHeader/AppHeader";
import AppFooter from "./Components/AppFooter/AppFooter";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Home from "./Components/Home/Home";
import Register from "./Components/Register/Register";
import Login from "./Components/Login/Login";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <AppHeader />
        <Space className="PageContent">
            <Routes>
                <Route path="/" element={<Home/>} />
                <Route path="/register" element={<Register/>} />
                <Route path="/login" element={<Login/>} />
            </Routes>
        </Space>
        <AppFooter />
      </BrowserRouter>
    </div>
  );
}

export default App;
