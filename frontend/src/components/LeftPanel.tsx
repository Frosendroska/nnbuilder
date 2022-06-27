import React, {useState} from 'react'
import './style/Panel.scss'
import incDecInput from './IncDecInput'
import * as api from 'nnbuilder-api'
import ProjectInfo from '../structure/ProjectInfo'
import {typesMap} from '../structure/Project'

type PanelProps = {
    taskQueueService: api.TasksQueueServicePromiseClient
    versionService: api.NNVersionServicePromiseClient

    projectInfo: ProjectInfo
}

function LeftPanel(props: PanelProps): JSX.Element {
    const [epochs, setEpochs] = useState(5)
    const [learningRate, setLearningRate] = useState(0.01)

    const taskRequest = () => {
        const request = new api.CreateTaskRequest
        props.taskQueueService.createTask(request)
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
            <div className='line'>
                <span className={'description'}>Target:</span>
                <div className='greyRect'>PetType</div>
            </div>
            <div className={'vertical-panel'}>
                <input type='submit' value='Load dataset'/>
                <input className={'submit-green'} type='submit' value='Train' onSubmit={taskRequest}/>
            </div>
        </div>
    )
}

export default LeftPanel
