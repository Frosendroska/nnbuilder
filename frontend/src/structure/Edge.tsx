import NeuronData from './NeuronData'

class Edge {
    from: NeuronData
    to: NeuronData
    weight: number

    constructor(from: NeuronData, to: NeuronData) {
        this.from = from
        this.to = to
        this.weight = 0
    }
}

export default Edge
