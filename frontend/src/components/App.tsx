import React from 'react'
import * as api from 'nnbuilder-api'
import Login from "./Login"
import Editor from "./Editor"
import {Routes, Route, Navigate} from 'react-router-dom'
import Header from "./Header";
import {persistentAtom} from "@nanostores/persistent";
import Projects from "./Projects";
import Register from "./Register";
import {useStore} from "@nanostores/react";


type AppProps = {
    authService: api.AuthServicePromiseClient
    modificationService: api.NNModificationServicePromiseClient
}

export const token = persistentAtom<string>("")

export default function App(props: AppProps): JSX.Element {
    let user = useStore(token)
    return (
        <>
            <Routes>
                <Route path="/" element={<Header/>}>
                    <Route
                        path="/projects"
                        element={user == "" ? <Navigate to="/login"/> : <Projects/>}/>;
                    <Route
                        path="/editor"
                        element={user == "" ? <Navigate to="/login"/> :
                            <Editor modificationService={props.modificationService}/>}/>;
                    <Route
                        path='/login' element={<Login authService={props.authService}/>}/>
                    <Route
                        path='/register'
                        element={<Register authService={props.authService}/>}/>
                </Route>
            </Routes>
        </>
    )
}
