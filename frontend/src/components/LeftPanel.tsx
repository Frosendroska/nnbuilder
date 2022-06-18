import React, {useState} from 'react'
import './style/LeftPanel.scss'
import IncDecInput from './IncDecInput';

function LeftPanel(): JSX.Element {
    const [epochs, setEpochs] = useState(5)
    const [learningRate, setLearningRate] = useState(0.01)
    // const [nntype, setNNtype] = useState('FF')
    // const [status, setStatus] = useState('not trained')
    // const [target, setTarget] = useState('pet type')

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
                <input type='submit' value='Load dataset'/>
                <input className={'submit-green'} type='submit' value='Train'/>
            </div>
        </div>
    )
}

export default LeftPanel
