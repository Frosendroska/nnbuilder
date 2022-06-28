import React, {useEffect, useState} from 'react'
import './style/Header.scss'
import './style/Projects.scss'
import * as api from 'nnbuilder-api'
import {token} from './App'
import {useStore} from '@nanostores/react'
import {typesMap, actionsMap} from '../structure/Project'
import Project from '../structure/Project'

type ProjectsProps = {
    userAccountService: api.UserAccountServicePromiseClient
    modificationService: api.NNModificationServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
    datasetService: api.DatasetServicePromiseClient
    chooseProject: (id: number | undefined, versionId: number | undefined) => void
}

function projectComponent(project: Project,
    selectProject: (project: Project) => void,
    deleteProject: (id: number) => void) {
    return <div key={project.id} className={'project-wrapper'}>
        <div className={'project clickable'} onClick={() => selectProject(project)}>
            <h3>{project.name}</h3>
            <div className={'project-version'}>Versions: {project.versions.length}</div>
            <div>
                <div>Type: {typesMap.get(project.type)}</div>
                <div>Status: {project.status}</div>
                <div>Action: {actionsMap.get(project.action)}</div>
            </div>
        </div>
        <div className={'project-exit no-select'} onClick={() => deleteProject(project.id)}>x</div>
    </div>
}

function addNewProject(addProject: () => void) {
    return <div className={'project-wrapper no-select'}>
        <div className={'project clickable'} onClick={addProject}>
            <div className={'project-plus'}>+</div>
            <div className={'project-add-label'}>add new project</div>
        </div>
    </div>
}

function Projects(props: ProjectsProps): JSX.Element {
    const user = useStore(token)
    const [step, setStep] = useState(0)
    const [newProjectName, setNewProjectName] = useState('')
    const [type, setType] = useState(0)
    const [action, setAction] = useState(0)
    const [username, setUserName] = useState<string>('')
    const [projects, setProjects] = useState<Project[]>([])

    const tokenInfo = {'Authorization': 'Bearer ' + user}

    async function readFile(file: File): Promise<ArrayBuffer> {
        return new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.addEventListener('loadend', (e) => resolve(e.target!.result as ArrayBuffer))
            reader.addEventListener('error', reject)
            reader.readAsArrayBuffer(file)
        })
    }

    async function handleSubmission(file: File) {
        const request = new api.UploadDatasetRequest()
            .setContent(new Uint8Array(await readFile(file)))
            .setTargetcolumnname('')
        console.log(request)
        props.datasetService.uploadDataset(request, tokenInfo)
    }

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
                new Project(
                    project.getId(),
                    project.getName(),
                    project.getVersionsList(),
                    project.getNntype(),
                    project.getActiontype(),
                    'Inference',
                ))
            setProjects(projects)
        })
    }


    const createNewProject = ((name: string, value: number) => {
        const request = new api.NNCreationRequest().setName(name).setNntype(value).setActiontype(action)
        props.modificationService.createnn(request, tokenInfo).then(() => {
            setStep(0)
            getAllProjects()
        })
    })

    const deleteProject = (id: number) => {
        const request = new api.DeleteProjectRequest().setProjectid(id)
        props.versionService.deleteProject(request).then(() => {
            getAllProjects()
        })
    }

    const selectProject = (project: Project) => {
        props.chooseProject(project.id, project.versions[project.versions.length - 1])
        window.location.href = '/editor'
    }

    useEffect(() => getAllProjects(), [])

    return (
        <>
            {step != 0 ?
                <div className={'projects-back'} onClick={() => setStep(0)}>Back to projects</div> : <></>
            }
            {
                step == 0 ?
                    <div className={'projects-page'}>
                        <div className={'hello-user'}>Hello, {username}, here will be your projects!</div>
                        <h2>Run learnt</h2>
                        <div className={'learnt'}>
                            <select>
                                {projects.map((project) =>
                                    <option key={project.id}>{project.name}</option>,
                                )}
                            </select>
                            <label htmlFor='loadDataset' className={'load-dataset'}>
                                Load dataset
                                <input id='loadDataset' type='file' onChange={(event) => {
                                    const files = event.target.files
                                    if (files != null && files.length > 0) {
                                        handleSubmission(files[0])
                                    }
                                }}
                                />
                            </label>
                            <input type={'submit'} className={'submit-green'} value={'Get started'}/>
                        </div>
                        <h2>Projects</h2>
                        <div className={'projects'}>{
                            projects.map((project) => projectComponent(project, selectProject, deleteProject))
                        }
                        {addNewProject(() => setStep(1))}
                        </div>
                    </div> : step == 1 ? <div className={'project-dialog'}>
                        <div className={'project-dialog-header'}>Select the option</div>
                        <div className={'project-dialog-options'}>
                            <input type={'submit'} value={'Create new NN'} className={'submit-grey'}
                                onClick={() => setStep(2)}/>
                            <input type={'submit'} value={'Load learnt model'} className={'submit-grey'}/>
                        </div>
                    </div> : <div className={'project-dialog'}>
                        <div className={'project-dialog-header'}>New NN settings</div>
                        <div className={'project-dialog-settings'}>
                            <div>Project&apos;s name</div>
                            <input type={'text'} value={newProjectName}
                                onChange={((event) => setNewProjectName(event.target.value))}/>
                            <div>Select NN type</div>
                            <select value={type} onChange={(event) => setType(Number(event.target.value))}>
                                <option value={'0'}>Feed Forward</option>
                                <option value={'1'}>Recurrent Neural Network</option>
                                <option value={'2'}>Long Term Memory</option>
                                <option value={'3'}>Deep Convolutional Network</option>
                            </select>
                            <div>Select action</div>
                            <select value={action} onChange={(event) => setAction(Number(event.target.value))}>
                                <option value={'0'}>Classification</option>
                                <option value={'1'}>Regression</option>
                            </select>
                            <input type={'submit'} className={'submit-green'} value={'Create'}
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
