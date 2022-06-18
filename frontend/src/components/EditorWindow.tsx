import React, {useEffect, useState} from 'react'
import * as d3 from 'd3'
import LayerData from '../structure/LayerData'
import Edge from '../structure/Edge'
import NeuronData from '../structure/NeuronData'
import './style/LeftPanel.scss'
import IncDecInput from './IncDecInput'
import LayerSettings from './LayerSettings'

function EditorWindow() {
    const [layers, setLayers] = useState<LayerData[]>([])
    const [edges, setEdges] = useState<Edge[]>([])

    d3.select('#layers').select('g')
        .selectAll('rect')
        .data(layers)
        .join('rect')
        .attr('x', 10)
        .attr('y', (layerData) => {
            return layerData.id * 115
        })
        .attr('width', 300)
        .attr('height', 100)
        .attr('rx', 5)
        .attr('ry', 5)
        .attr('fill', '#D9D9D9')

    const edgesContainer = d3.select('#layers').select('g')
        .selectAll('line')
        .data(edges)
        .join('line')
        .attr('x1', (d) => d.from.x)
        .attr('y1', (d) => d.from.y)
        .attr('x2', (d) => d.to.x)
        .attr('y2', (d) => d.to.y)
        .attr('stroke', '#D95555')

    const neuronsContainer = d3.select('#layers').select('g')
        .selectAll('circle')
        .data(layers.flatMap((x) => x.neurons))
        .join('circle')
        .attr('r', 10)
        .attr('x', 150)
        .attr('y', (d: NeuronData) => d.layer_id * 115 + 50)
        .attr('fill', '#D95555')

    const simulation = d3.forceSimulation(layers.flatMap((x) => x.neurons))
        .force('collideForce', d3.forceCollide().radius(20).strength(0.1))
        .force('x', d3.forceX(function () {
                return 150
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

        d3.select('#layers')
            .append('svg')
            .attr('style', 'width: 100%; height: 100%;').append('g')
    }, [])

    function add(n: number = 1) {
        const layersId = layers.length
        const newLayers = [...Array(n).keys()].map((i) => new LayerData(layersId + i))

        if (layersId > 0) {
            let oldLayer = layers[layers.length - 1]
            setEdges((prev) =>
                prev.concat(
                    newLayers.flatMap(layer => {
                        const result = layer.neurons.flatMap(to => oldLayer.neurons
                            .flatMap(from => new Edge(from, to)))
                        oldLayer = layer
                        return result
                    }),
                )
            )
        }
        setLayers((prev) => prev.concat(newLayers))
    }

    function remove(n: number = 1) {
        const lastLayerIds = new Set([...Array(n).keys()].map((i) => layers[layers.length - i - 1].id))
        setLayers((prev) => prev.slice(0, -n))
        setEdges((prev) => prev.filter((e) => !lastLayerIds.has(e.to.layer_id)))
    }

    const neuronsStyle = {width: '800px', height: layers.length * 115 + 'px'}

    const setLayersAmount = (amount: number) => {
        const change = amount - layers.length
        change < 0 ? remove(-change) : add(change)
    }

    return (
        <div className='Editor'>
            <div className='Window'>
                <h2>Editor Window</h2>
                Layers: {layers.length}
                {IncDecInput(layers.length, setLayersAmount, 1, true)}
                <LayerSettings/>
                <div id='layers' style={neuronsStyle}> </div>
            </div>
        </div>
    )
}

export default EditorWindow
