import * as d3 from 'd3'

class NeuronData implements d3.SimulationNodeDatum {
    layer_id: number
    id: number
    x: number
    y: number
    fx: number | null
    fy: number | null

    constructor(layerId: number, id: number) {
        this.layer_id = layerId
        this.id = id
        this.x = 400
        this.y = this.layer_id * 115 + 50
        this.fx = null
        this.fy = null
    }
}

export default NeuronData
