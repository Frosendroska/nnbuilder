import React from 'react';
import './style/Header.scss';
import {Link, Outlet} from "react-router-dom";

function Header(): JSX.Element{
    return(
        <>
            <header className="header">
                <Link to="/projects">Projects</Link>
                <Link to="/editor">Editor</Link>
                <Link to="/login">Login</Link>
                <Link to="/register">Register</Link>
            </header>
            <Outlet/>
        </>
    );
}

export default Header;