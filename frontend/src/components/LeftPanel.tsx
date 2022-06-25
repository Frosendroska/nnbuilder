import React, {useState} from 'react'
import './style/Panel.scss'
import IncDecInput from './IncDecInput';
import * as api from 'nnbuilder-api'

type PanelProps = {
    taskQueueService: api.TasksQueueServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function LeftPanel(props: PanelProps): JSX.Element {
    const [epochs, setEpochs] = useState(5)
    const [learningRate, setLearningRate] = useState(0.01)

    const taskRequest = () => {
        const request = new api.CreateTaskRequest
        props.taskQueueService.createTask(request)
        return true;
    }

    return (
        <div className='left'>
            <div className='line'>
                <span className='description big'>Status:</span>
                <div className='greyRect'>Trained</div>
            </div>
            <div className='line'>
                <span className={'description'}>NNtype:</span>
                <div className='greyRect'>Recurrent</div>
            </div>
            <div className='line-centered'>
                    <div className={'description'}>Epochs</div>
                    {IncDecInput(epochs, setEpochs, 1, true)}
            </div>
            <div className='line-centered'>
                    <div className={'description'}>Learning rate</div>
                    {IncDecInput(learningRate, setLearningRate, 0.01, false)}
            </div>
            <div className='line'>
                <span className={'description'}>Target:</span>
                <div className='greyRect'>PetType</div>
            </div>
            <div>
                <input type='submit' value='Load dataset' onSubmit={()=>{}}/>
                <input className={'submit-green'} type='submit' value='Train' onSubmit={taskRequest}/>
            </div>
        </div>
    )
}

export default LeftPanel
