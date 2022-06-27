import React, {useState, useEffect} from 'react'
import './style/LayerSettings.scss'
import IncDecInput from './IncDecInput'
import LayerData from "../structure/LayerData";
import Project from "../structure/Project";
import * as api from 'nnbuilder-api'
import {useStore} from "@nanostores/react";
import {currentProject, token} from "./App";

type LayersProps = {
    modificationService: api.NNModificationServicePromiseClient
    layers: LayerData[]
    updateLayer: (layer: LayerData, i: number) => void
}

type LayerProps = {
    modificationService: api.NNModificationServicePromiseClient
    layer: LayerData
    updateLayer: (layer: LayerData) => void
}

function LayerSettings(props: LayerProps): JSX.Element {
    const project = useStore(currentProject)

    const updateLayer = (layer: LayerData) => {
        props.updateLayer(layer)
        const request = new api.NNModificationRequest().setNnid(Number(project))
            .setChangeactivationfunction(new api.ChangeActivationFunction()
                .setIndex(props.layer.id)
                .setF(layer.activation))
            .setChangenumberofneuron(new api.ChangeNumberOfNeuron()
                .setIndex(props.layer.id)
                .setNumber(layer.neurons.length))
        props.modificationService.modifynn(request).then((result: api.NNModificationResponse) => {
            console.log(result.getException())
            console.log(result)
            console.log(result)
            console.log(result)
        })
    }

    return (<div className='layer-settings'>
        <div className='line-centered'>
            <div className={'description'}>Neurons</div>
            {IncDecInput(props.layer.neurons.length, (amount) => {
                    console.log(amount)
                return updateLayer(
                    new LayerData(
                        props.layer.id,
                        amount,
                        props.layer.type,
                        props.layer.activation
                    ))
                }, 1, true, 1, 100)}
        </div>
        <div className='line-centered'>
            <div className={'description'}>Activation</div>
            <select name='activation' onChange={(event) => props.updateLayer(
                new LayerData(
                    props.layer.id,
                    props.layer.neurons.length,
                    props.layer.type,
                    Number(event.target.value))
            )}>
                <option value='1'>None</option>
                <option value='2'>Linear</option>
                <option value='3'>Sigmoid</option>
                <option value='4'>Tanh</option>
                <option value='5'>ReLU</option>
                <option value='6'>LeakyReLU</option>
                <option value='7'>Max</option>
                <option value='8'>BinaryStep</option>
                <option value='9'>Gaussian</option>
            </select>
        </div>
        <div className='line-centered'>
            <div className={'description'}>Type</div>
            <select name='type' onChange={(event) => updateLayer(
                new LayerData(
                    props.layer.id,
                    props.layer.neurons.length,
                    Number(event.target.value),
                    props.layer.activation)
            )}>
                <option value='0'>InputCell</option>
                <option value='1'>BackfedInputCell</option>
                <option value='2'>NoisyInputCell</option>
                <option value='3'>HiddenCell</option>
                <option value='4'>ProbablisticHiddenCell</option>
                <option value='5'>SpikingHiddenCell</option>
                <option value='6'>CapculeCell</option>
                <option value='7'>OutputCell</option>
                <option value='8'>MatchInputOutputCell</option>
                <option value='9'>RecurrentCell</option>
                <option value='10'>MemoryCell</option>
                <option value='11'>GatedMemoryCell</option>
                <option value='12'>Kernel</option>
                <option value='13'>ConvolutionalOrPool</option>
                <option value='14'>Undefined</option>
            </select>
        </div>
    </div>)
}

function LayersSettings(props: LayersProps): JSX.Element {
    return (
        <div className={"all-layers-settings"}>
            <div className={"settings-label"}>Settings</div>
            {props.layers.map((layerData, i) => {
                return <LayerSettings key={layerData.id}
                                      modificationService={props.modificationService}
                                      updateLayer={(layer) => props.updateLayer(layer, i)}
                                      layer={layerData}/>
            })}
        </div>
    )
}

export default LayersSettings
