import React, {useEffect, useState} from 'react'
import './style/Header.scss'
import './style/Projects.scss'
import * as api from 'nnbuilder-api'
import {token} from "./App";
import {useStore} from "@nanostores/react";
import Project from '../structure/Project';

type ProjectsProps = {
    userAccountService: api.UserAccountServicePromiseClient
    modificationService: api.NNModificationServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function ProjectComponent(project: Project, deleteProject: (id: number) => void) {
    return <div key={project.id} className={"project-wrapper"}>
        <div className={"project clickable"} onClick={() => window.location.href = "/editor"}>
            <h3>{project.name}</h3>
            <div className={"project-version"}>Versions: {project.versions}</div>
            <div>
                <div>Type: {project.type}</div>
                <div>Status: {project.status}</div>
            </div>
        </div>
        <div className={"project-exit no-select"} onClick={() => deleteProject(project.id)}>x</div>
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
    const [type, setType] = useState(0)
    const [username, setUserName] = useState<string>('');
    const [projects, setProjects] = useState<Project[]>([])

    const tokenInfo = {"Authorization": "Bearer " + user}

    useEffect(() => {
        const request = new api.GetNameRequest()
        props.userAccountService.getName(request, tokenInfo).then((result) => {
            setUserName(result.getName())
        })
    }, [])

    const getAllProjects = () => {
        const request = new api.GetProjectsRequest()
        props.userAccountService.getProjects(request, tokenInfo).then((result) => {
            const projects = result.getProjectList().map((project) =>
                new Project(project.getId(), project.getName(), project.getVersions(), "Recurrent", "Inference"))
            setProjects(projects)
        })
    }


    const createNewProject = ((name: string, value: number) => {
        const request = new api.NNCreationRequest().setName(name).setNntype(value)
        props.modificationService.createnn(request, tokenInfo).then((result) => {
            setStep(0)
            getAllProjects()
        })
    })

    const deleteProject = (id: number) => {
        const request = new api.deleteProjectRequest().setProjectid(id)
        props.versionService.deleteProject(request).then((result) => {
            getAllProjects()
        })
    }

    useEffect(() => getAllProjects(), [])

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
                            projects.map(project => ProjectComponent(project, deleteProject))
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
                            <select value={type} onChange={(event) => setType(Number(event.target.value))}>
                                <option value={"0"}>Feed Forward</option>
                                <option value={"1"}>Recurrent Neural Network</option>
                                <option value={"2"}>Long Term Memory</option>
                                <option value={"3"}>Deep Convolutional Network</option>
                            </select>
                            <div>Select action</div>
                            <select>
                                <option>Classification</option>
                            </select>
                            <input type={"submit"} className={"submit-green"} value={"Create"}
                                   onClick={() => {
                                       createNewProject(newProjectName, type)
                                   }}/>
                        </div>
                    </div>
            }
        </>
    )
}

export default Projects
