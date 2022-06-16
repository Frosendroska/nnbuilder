import React from 'react';
import './style/Header.scss';
import {Link, Outlet} from "react-router-dom";
import {token} from "./App";
import {useStore} from "@nanostores/react";

function Header(): JSX.Element {
    let user = useStore(token)
    return (
        <>
            <header className="header">
                {/*<Link to="/editor">Editor</Link>*/}
                {user == "" ? <><Link to="/register">Register</Link>
                    <Link to="/login">Log in</Link></> : <><Link to="/projects">Projects</Link> <Link to="/login" onClick={(event) => {
                    let intention = window.confirm("Are you sure you want to log out?")
                    intention ? token.set("") : event.preventDefault()
                }}> Log out </Link></>}
            </header>
            <Outlet/>
        </>
    );
}

export default Header;