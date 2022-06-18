import React, {useState} from 'react'
import './style/LayerSettings.scss'
import IncDecInput from './IncDecInput'

function LayerSettings(): JSX.Element {
    const [neurons, setNeurons] = useState(1)
    //const [activation, setActivation] = useState(0.01)
    //const [type, setType] = useState(0.01)

    return (
        <div className='layer-settings'>
            <div className='line-centered'>
                <div className={'description'}>Neurons</div>
                {IncDecInput(neurons, setNeurons, 1, true)}
            </div>
            <div className='line-centered'>
                <div className={'description'}>Activation</div>
                <select name='activation'>
                    <option value='sin'>sin</option>
                    <option value='cos'>cos</option>
                    <option value='tan'>tan</option>
                </select>
            </div>
            <div className='line-centered'>
                <div className={'description'}>Type</div>
                <select name='type'>
                    <option value='sin'>sin</option>
                    <option value='cos'>cos</option>
                    <option value='tan'>tan</option>
                </select>
            </div>

        </div>
    )
}

export default LayerSettings
