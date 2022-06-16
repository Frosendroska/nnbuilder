import * as d3 from 'd3'
import NeuronData from './NeuronData'

function getRandomInt(min: number, max: number) {
    return min + Math.floor(Math.random() * (max - min))
}

class LayerData implements d3.SimulationNodeDatum {
    id: number
    neurons: NeuronData[]
    x: number
    y: number
    fx: number | null
    fy: number | null

    constructor(id: number) {
        this.id = id
        this.x = 0
        this.y = 0
        this.fx = null
        this.fy = null
        this.neurons = [...Array(getRandomInt(3, 10))].map((index) => new NeuronData(id, index))
    }
}

export default LayerData
