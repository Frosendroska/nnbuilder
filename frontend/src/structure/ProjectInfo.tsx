import LayerData from './LayerData'

class ProjectInfo {
    type: number
    layerData: LayerData[]

    constructor(type: number, layerData: LayerData[]) {
        this.type = type
        this.layerData = layerData
    }
}

export default ProjectInfo
