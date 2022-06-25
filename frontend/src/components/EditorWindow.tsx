import React, {useEffect, useState} from 'react'
import * as d3 from 'd3'
import LayerData from '../structure/LayerData'
import Edge from '../structure/Edge'
import NeuronData from '../structure/NeuronData'
import './style/Panel.scss'
import * as api from 'nnbuilder-api'
import IncDecInput from './IncDecInput'
import LayersSettings from './LayersSettings'

type EditorWindowProps = {
    modificationService: api.NNModificationServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function calculateEdges(layers: LayerData[]): Edge[] {
    let prev: LayerData | undefined = undefined
    return layers.flatMap(layer => {
        let result: Edge[] = []
        if (prev !== undefined) {
            result = prev.neurons.flatMap(from => layer.neurons
                .flatMap(to => new Edge(from, to)))
        }
        prev = layer
        return result
    })
}

function EditorWindow(props: EditorWindowProps) {
    const [layers, setLayers] = useState<LayerData[]>([])
    const edges = calculateEdges(layers)

    const width = (layerData: LayerData) => {
        return 200 + (800 - 200) * layerData.neurons.length / 100 //(Math.atan(layerData.neurons.length/100)) * 200 + 100
    }
    const center = () => {
        return ((800 - 10) / 2)
    }

    d3.select('#layers').select('g.layers')
        .selectAll('rect')
        .data(layers)
        .join('rect')
        .attr('x', (layerData) => center() - width(layerData) / 2)
        .attr('y', (layerData) => layerData.id * 115)
        .attr('width', (layerData) => width(layerData))
        .attr('height', 100)
        .attr('rx', 5)
        .attr('ry', 5)
        .attr('fill', '#D9D9D9')

    const edgesContainer = d3.select('#layers').select('g.graph')
        .selectAll('line')
        .data(edges)
        .join('line')
        .attr('x1', (d) => d.from.x)
        .attr('y1', (d) => d.from.y)
        .attr('x2', (d) => d.to.x)
        .attr('y2', (d) => d.to.y)
        .attr('stroke', '#D95555')

    const neuronsContainer = d3.select('#layers').select('g.graph')
        .selectAll('circle')
        .data(layers.flatMap((x) => x.neurons))
        .join('circle')
        .attr('r', 3)
        .attr('x', 150)
        .attr('y', (d: NeuronData) => d.layer_id * 115 + 50)
        .attr('fill', '#D95555')

    const simulation = d3.forceSimulation(layers.flatMap((x) => x.neurons))
        .force('collideForce', d3.forceCollide().radius(20).strength(0.1))
        .force('x', d3.forceX(function () {
                return 400
            }).strength(0.025),
        )
        .force('y', d3.forceY(function (d: NeuronData) {
            return d.layer_id * 115 + 50
        }).strength(0.5))
        .alphaDecay(0.01)

    simulation.on('tick', () => {
        neuronsContainer
            .attr('cx', (d) => d.x)
            .attr('cy', (d) => d.y)
        edgesContainer
            .attr('x1', (d) => d.from.x)
            .attr('y1', (d) => d.from.y)
            .attr('x2', (d) => d.to.x)
            .attr('y2', (d) => d.to.y)
    })

    useEffect(() => {
        // call api and get layers
        setLayers([new LayerData(0)])

        const svg = d3.select('#layers')
            .append('svg')
            .attr('style', 'width: 100%; height: 100%;')
        svg.append('g').attr("class", "layers")
        svg.append('g').attr("class", "graph")
    }, [])

    function add(n: number = 1) {
        const layersId = layers.length
        const newLayers = [...Array(n).keys()].map((i) => new LayerData(layersId + i))
        setLayers((prev) => prev.concat(newLayers))
    }

    function remove(n: number = 1) {
        //const lastLayerIds = new Set([...Array(n).keys()].map((i) => layers[layers.length - i - 1].id))
        setLayers((prev) => prev.slice(0, -n))
    }

    const neuronsStyle = {width: 1000, height: layers.length * 115 + 'px'}

    const setLayersAmount = (amount: number) => {
        const change = amount - layers.length
        change < 0 ? remove(-change) : add(change)
    }

    const updateLayer = (layer: LayerData, i: number) => {
        setLayers((prev) => {
            const result = new Array(...prev)
            result[i] = layer
            return result
        })
    }

    return (
        <div className='editor'>
            Layers: {layers.length}
            {IncDecInput(layers.length, setLayersAmount, 1, true, 1, 100)}
            <div className={"layers-with-settings"}>
                <LayersSettings updateLayer={updateLayer} layers={layers}/>
                <div id='layers' style={neuronsStyle}> </div>
            </div>
        </div>
    )
}

export default EditorWindow