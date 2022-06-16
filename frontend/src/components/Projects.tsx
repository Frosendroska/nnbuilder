import React from 'react'
import './style/Header.scss'
import {token} from './App'
import {useStore} from '@nanostores/react'
import {Link} from 'react-router-dom'

function Projects(): JSX.Element {
    const user = useStore(token)
    return (
        <>
            Hello, {user}, here will be your projects!
            <Link to='/editor'>Editor</Link>
        </>
    )
}

export default Projects
