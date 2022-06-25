import NeuronData from './NeuronData'

function getRandomInt(min: number, max: number) {
    return min + Math.floor(Math.random() * (max - min))
}

class LayerData {
    id: number
    neurons: NeuronData[]

    constructor(id: number, neuronsAmount: number = getRandomInt(3, 10)) {
        this.id = id
        this.neurons = [...Array(neuronsAmount)].map((index) => new NeuronData(id, index))
    }
}

export default LayerData
