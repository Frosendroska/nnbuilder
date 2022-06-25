import React from 'react'
import './style/LayerSettings.scss'
import IncDecInput from './IncDecInput'
import LayerData from "../structure/LayerData";

type LayersProps = {
    layers: LayerData[]
    updateLayer: (layer: LayerData, i: number) => void
}

type LayerProps = {
    layer: LayerData
    updateLayer: (layer: LayerData) => void
}

function LayerSettings(props: LayerProps): JSX.Element {
    return (<div className='layer-settings'>
        <div className='line-centered'>
            <div className={'description'}>Neurons</div>
            {IncDecInput(props.layer.neurons.length, (amount) => {
                props.updateLayer(
                    new LayerData(
                        props.layer.id,
                        amount
                    )
                )
            }, 1, true, 1, 100)}
        </div>
        <div className='line-centered'>
            <div className={'description'}>Activation</div>
            <select name='activation'>
                <option value='None'>None</option>
                <option value='Linear'>Linear</option>
                <option value='Sigmoid'>Sigmoid</option>
                <option value='Tanh'>Tanh</option>
                <option value='ReLU'>ReLU</option>
                <option value='LeakyReLU'>LeakyReLU</option>
                <option value='Max'>Max</option>
                <option value='BinaryStep'>BinaryStep</option>
                <option value='Gaussian'>Gaussian</option>
            </select>
        </div>
        <div className='line-centered'>
            <div className={'description'}>Type</div>
            <select name='type'>
                <option value='InputCell'>InputCell</option>
                <option value='BackfedInputCell'>BackfedInputCell</option>
                <option value='NoisyInputCell'>NoisyInputCell</option>
                <option value='HiddenCell'>HiddenCell</option>
                <option value='ProbablisticHiddenCell'>ProbablisticHiddenCell</option>
                <option value='SpikingHiddenCell'>SpikingHiddenCell</option>
                <option value='CapculeCell'>CapculeCell</option>
                <option value='OutputCell'>OutputCell</option>
                <option value='MatchInputOutputCell'>MatchInputOutputCell</option>
                <option value='RecurrentCell'>RecurrentCell</option>
                <option value='MemoryCell'>MemoryCell</option>
                <option value='GatedMemoryCell'>GatedMemoryCell</option>
                <option value='Kernel'>Kernel</option>
                <option value='ConvolutionalOrPool'>ConvolutionalOrPool</option>
                <option value='Undefined'>Undefined</option>
            </select>
        </div>
    </div>)
}

function LayersSettings(props: LayersProps): JSX.Element {
    return (
        <div>
            {props.layers.map((layerData, i) => {
                return <LayerSettings key={layerData.id}
                                      updateLayer={(layer) => props.updateLayer(layer, i)}
                                      layer={layerData}/>
            })}
        </div>
    )
}

export default LayersSettings
