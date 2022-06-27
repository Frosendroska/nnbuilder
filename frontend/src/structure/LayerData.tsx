import NeuronData from './NeuronData'

function getRandomInt(min: number, max: number) {
    return min + Math.floor(Math.random() * (max - min))
}

// export const layerTypeMap = new Map<number, string>(
//     [[0, 'InputCell'],
//     [1, 'BackfedInputCell'],
//     [2, 'NoisyInputCell'],
//     [3, 'HiddenCell'],
//     [4, 'ProbablisticHiddenCell'],
//     [5, 'SpikingHiddenCell'],
//     [6, 'CapculeCell'],
//     [7, 'OutputCell'],
//     [8, 'MatchInputOutputCell' ],
//     [9, 'RecurrentCell' ],
//     [10, 'MemoryCell'],
//     [11, 'GatedMemoryCell '],
//     [12, 'Kernel'],
//     [13, 'ConvolutionalOrPool'],
//     [14, 'Undefined']]
// )
//
// export const activationTypeMap = [
//     [0, 'None']
// Linear
// Sigmoid
// Tanh
// ReLU
// LeakyReLU
// Max
// BinaryStep
// Gaussian
// ]

class LayerData {
    id: number
    neurons: NeuronData[]
    type: number
    activation: number

    constructor(id: number, neuronsAmount: number = getRandomInt(3, 10),
        type = 0,
        activation = 0) {
        this.id = id
        this.neurons = [...Array(neuronsAmount)].map((index) => new NeuronData(id, index))
        this.type = type
        this.activation = activation
    }
}

export default LayerData
