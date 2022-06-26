import React, {useState} from 'react'
import './style/Header.scss'
//import {token} from './App'
//import {useStore} from '@nanostores/react'
//import {Link} from 'react-router-dom'
import * as api from 'nnbuilder-api'

type ProjectsProps = {
    userAccountService: api.UserAccountServicePromiseClient
}

class Project {
    name: string
    version: string
    type: string
    status: string

    constructor(name: string, version: string, type: string, status: string) {
        this.name = name
        this.version = version
        this.type = type
        this.status = status
    }
}

function ProjectComponent(project: Project) {
    return <div className={"project"}>
        <div className={"project-exit"}>X</div>
        <h3>{project.name}</h3>
        <div className={"project-version"}>Version: {project.version}</div>
        <div>
            <div>Type: {project.type}</div>
            <div>Status: {project.status}</div>
        </div>
    </div>
}

function Projects(props: ProjectsProps): JSX.Element {
    const [username, setUserName] = useState<string>('');
    const [projects] = useState<Project[]>([
        new Project("Cat or dog?", "2/5", "Reccurent", "Inference"),
        new Project("Apple or orange?", "2/5", "Reccurent", "Inference"),
        new Project("Maks or Ann?", "26/6", "Reccurent", "Inference"),
    ])

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
            <div className={"projects-page"}>

            <h2>Run learnt</h2>
            <div className={"learnt"}>
                <select>
                    {projects.map(project =>
                        <option>{project.name}</option>
                    )}
                </select>
                <input type={"submit"} value={"Load dataset"}/>
                <input type={"submit"} className={"submit-green"} value={"Get started"}/>
            </div>
            <h2>Projects</h2>
            <div className={"projects"}>{
                projects.map(project => ProjectComponent(project))
            }
            </div>
            </div>
            {/*<Link to='/editor'>Editor</Link>*/}
        </>
    )
}

export default Projects
