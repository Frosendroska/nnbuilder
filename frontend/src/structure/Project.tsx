import * as api from 'nnbuilder-api'

export const typesMap = new Map<number, string>([
    [0, "Feed Forward"],
    [1, "Recurrent"],
    [2, "Long Term Memory"],
    [3, "Deep Convolutional"]
]);

export const actionsMap = new Map<number, string>([
    [0, "Classification"],
    [1, "Regression"]
]);

class Project {

    id: number
    name: string
    versions: number
    type: api.NetworkType
    action: api.ActionType
    status: string

    constructor(id: number, name: string, versions: number, type: api.NetworkType, action: api.ActionType, status: string) {
        this.id = id
        this.name = name
        this.versions = versions
        this.type = type
        this.action =action
        this.status = status
    }
}

export default Project