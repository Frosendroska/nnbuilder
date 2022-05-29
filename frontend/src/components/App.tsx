import React, {useCallback, useState} from 'react'
import * as api from 'nnbuilder-api'
import Login from "./Login"
import Editor from "./Editor"
import {Routes, Route, Link, Navigate} from 'react-router-dom'
import Header from "./Header";
import {atom} from "nanostores";
import Projects from "./Projects";
import Register from "./Register";


type AppProps = {
    authService: api.AuthServicePromiseClient
    modificationService: api.NNModificationServicePromiseClient
}

export const token = atom<string>('empty')

export default function App(props: AppProps): JSX.Element {
    return (
        <>
            <Routes>
                <Route path="/" element={<Header/>}>
                    <Route
                        path="/projects"
                        element={ token.get() === 'empty' ? <Navigate to="/login" /> : <Projects/> }/>;
                    <Route
                        path="/editor"
                        element={ token.get() === 'empty' ? <Navigate to="/login" /> : <Editor modificationService={props.modificationService}/> }/>;
                    <Route
                        path='/login' element = {<Login authService={props.authService}/>}/>
                    <Route
                        path='/register' element = {<Register authService={props.authService}/>}/>
                </Route>
            </Routes>
        </>
    )
}
