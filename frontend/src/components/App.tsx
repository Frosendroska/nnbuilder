import React from 'react'
import * as api from 'nnbuilder-api'
import Login from './Login'
import Editor from './Editor'
import {Routes, Route, Navigate} from 'react-router-dom'
import Header from './Header'
import {persistentAtom} from '@nanostores/persistent'
import Projects from './Projects'
import Register from './Register'
import {useStore} from '@nanostores/react'
import './style/Main.scss'

type AppProps = {
    authService: api.AuthServicePromiseClient
    modificationService: api.NNModificationServicePromiseClient
    taskQueueService: api.TasksQueueServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
    userAccountService: api.UserAccountServicePromiseClient
}

export const token = persistentAtom<string>('token', '')

export default function App(props: AppProps): JSX.Element {
    const user = useStore(token)
    console.log('current user token is: ', user)

    return (
        <>
            <Routes>
                <Route path='/' element={<Header/>}>
                    <Route
                        path='/projects'
                        element={user == '' ? <Navigate to='/login'/> :
                            <Projects modificationService={props.modificationService}
                                      userAccountService={props.userAccountService}
                                      versionService={props.versionService}/>}/>
                    <Route
                        path='/editor'
                        element={user == '' ? <Navigate to='/login'/> :
                            <Editor modificationService={props.modificationService}
                                    taskQueueService={props.taskQueueService} versionService={props.versionService}/>}/>
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
