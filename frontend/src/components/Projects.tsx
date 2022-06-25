import React, {useState} from 'react'
import './style/Header.scss'
//import {token} from './App'
//import {useStore} from '@nanostores/react'
import {Link} from 'react-router-dom'
import * as api from 'nnbuilder-api'

type ProjectsProps = {
    userAccountService: api.UserAccountServicePromiseClient
}

function Projects(props: ProjectsProps): JSX.Element {
    const [username, setUserName] = useState<string>('');

    //const user = useStore(token)

    const getName = () => {
        const request = new api.GetNameRequest()
        props.userAccountService.getName(request).then((result: api.GetNameResponse) => {
            setUserName(result.getName())
        })
        console.log(username)
        return username
    }

    return (
        <>
            {getName()}
            Hello, {username}, here will be your projects!
            <Link to='/editor'>Editor</Link>
        </>
    )
}

export default Projects
