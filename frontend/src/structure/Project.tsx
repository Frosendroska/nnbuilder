class Project {
    id: number
    name: string
    versions: number
    type: string
    status: string

    constructor(id: number, name: string, versions: number, type: string, status: string) {
        this.id = id
        this.name = name
        this.versions = versions
        this.type = type
        this.status = status
    }
}

export default Project