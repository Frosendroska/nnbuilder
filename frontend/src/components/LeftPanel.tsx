import React, {useState} from 'react'
import './style/Panel.scss'
import incDecInput from './IncDecInput'
import * as api from 'nnbuilder-api'
import ProjectInfo from '../structure/ProjectInfo'
import {typesMap} from '../structure/Project'
import {useStore} from '@nanostores/react'
import {currentVersion, token} from './App'

type PanelProps = {
    taskQueueService: api.TasksQueueServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
    datasetService: api.DatasetServicePromiseClient
    projectInfo: ProjectInfo
}


function LeftPanel(props: PanelProps): JSX.Element {
    const [epochs, setEpochs] = useState(5)
    const [learningRate, setLearningRate] = useState(0.01)
    const [target, setTarget] = useState('')
    const [dataset, setDataset] = useState(0)
    const user = useStore(token)
    const nnId = useStore(currentVersion)
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
            .setTargetcolumnname(target)
        props.datasetService.uploadDataset(request, tokenInfo).then((result) => {
            setDataset(result.getDatasetid())
        })
    }

    const taskRequest = () => {
        const request = new api.CreateTaskRequest()
            .setNnid(Number(nnId))
            .setDatasetid(dataset)
            .setEpochamount(Number(epochs))
        props.taskQueueService.createTask(request).then(() => {
            alert('Task created!')
        })
        return true
    }

    return (
        <div className='left'>
            <div className='line'>
                <span className='description big'>Status:</span>
                <div className='greyRect'>Inference</div>
            </div>
            <div className='line'>
                <span className={'description'}>NNtype:</span>
                <div className='greyRect'>{typesMap.get(props.projectInfo.type)}</div>
            </div>
            <div className='line-centered'>
                <div className={'description'}>Epochs</div>
                {incDecInput(epochs, setEpochs, 1, true)}
            </div>
            <div className='line-centered'>
                <div className={'description'}>Learning rate</div>
                {incDecInput(learningRate, setLearningRate, 0.01, false)}
            </div>
            <div className='line line-vertical'>
                <div className={'description'}>Target:</div>
                <input type='text' className='greyRect' placeholder={'Type target'}
                    value={target} onChange={(e) => setTarget(e.target.value)}/>
            </div>
            <div className={'vertical-panel'}>
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
                <input className={'submit-green'} type='submit' value='Train' onClick={taskRequest}/>
            </div>
        </div>
    )
}

export default LeftPanel
