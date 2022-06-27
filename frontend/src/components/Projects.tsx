import React, {useEffect, useState} from 'react'
import './style/Header.scss'
import './style/Projects.scss'
import * as api from 'nnbuilder-api'
import {token} from "./App";
import {useStore} from "@nanostores/react";

type ProjectsProps = {
    userAccountService: api.UserAccountServicePromiseClient
}

class Project {
    id: number
    name: string
    versions: number
    type: string
    status: string

    constructor(id: number, name: string, versions: number, type: string, status: string) {
        this.id = id
        this.name = name
        this.versions = versions
        this.type = type
        this.status = status
    }
}

function ProjectComponent(project: Project) {
    return <div key={project.id} className={"project-wrapper"}>
        <div className={"project clickable"} onClick={() => window.location.href = "/editor"}>
            <h3>{project.name}</h3>
            <div className={"project-version"}>Versions: {project.versions}</div>
            <div>
                <div>Type: {project.type}</div>
                <div>Status: {project.status}</div>
            </div>
        </div>
        <div className={"project-exit no-select"}>x</div>
    </div>
}

function AddNewProject(addProject: () => void) {
    return <div className={"project-wrapper no-select"}>
        <div className={"project clickable"} onClick={addProject}>
            <div className={"project-plus"}>+</div>
            <div className={"project-add-label"}>add new project</div>
        </div>
    </div>
}

function Projects(props: ProjectsProps): JSX.Element {
    const user = useStore(token)
    const [step, setStep] = useState(0)
    const [newProjectName, setNewProjectName] = useState("")
    const [username, setUserName] = useState<string>('');
    const [projects] = useState<Project[]>([
        new Project(1, "Cat or dog?", 5, "Reccurent", "Inference"),
        new Project(2, "Apple or orange?", 3, "Reccurent", "Inference"),
        new Project(3, "Maks or Ann?", 6, "Reccurent", "Inference"),
    ])

    useEffect(() => {
        const request = new api.GetNameRequest()
        props.userAccountService.getName(request, {"Authorization": "Bearer " + user}).then((result) => {
            setUserName(result.getName())
        })
    }, [])

    return (
        <>
            {step != 0 ?
                <div className={"projects-back"} onClick={() => setStep(0)}>Back to projects</div> : <></>
            }
            {
                step == 0 ?
                    <div className={"projects-page"}>
                        Hello, {username}, here will be your projects!
                        <h2>Run learnt</h2>
                        <div className={"learnt"}>
                            <select>
                                {projects.map(project =>
                                    <option key={project.id}>{project.name}</option>
                                )}
                            </select>
                            <input type={"submit"} value={"Load dataset"}/>
                            <input type={"submit"} className={"submit-green"} value={"Get started"}/>
                        </div>
                        <h2>Projects</h2>
                        <div className={"projects"}>{
                            projects.map(project => ProjectComponent(project))
                        }
                            {AddNewProject(() => setStep(1))}
                        </div>
                    </div> : step == 1 ? <div className={"project-dialog"}>
                        <div className={"project-dialog-header"}>Select the option</div>
                        <div className={"project-dialog-options"}>
                            <input type={"submit"} value={"Create new NN"} className={"submit-grey"}
                                   onClick={() => setStep(2)}/>
                            <input type={"submit"} value={"Load learnt model"} className={"submit-grey"}/>
                        </div>
                    </div> : <div className={"project-dialog"}>
                        <div className={"project-dialog-header"}>New NN settings</div>
                        <div className={"project-dialog-settings"}>
                            <div>Project's name</div>
                            <input type={"text"} value={newProjectName}
                                   onChange={(event => setNewProjectName(event.target.value))}/>
                            <div>Select NN type</div>
                            <select>
                                <option value={"ff"}>Feed Forward</option>
                                <option value={"rnn"}>Recurrent Neural Network</option>
                                <option value={"ltm"}>Long Term Memory</option>
                                <option value={"dcn"}>Deep Convolutional Network</option>
                            </select>
                            <div>Select action</div>
                            <select>
                                <option>Classification</option>
                            </select>
                            <input type={"submit"} className={"submit-green"} value={"Create"}/>
                        </div>
                    </div>
            }
        </>
    )
}

export default Projects
