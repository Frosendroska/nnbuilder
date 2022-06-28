import React, {useEffect, useState} from 'react'
import EditorWindow from './EditorWindow'
import * as api from 'nnbuilder-api'
import LeftPanel from './LeftPanel'
import RightPanel from './RightPanel'
import {token} from './App'
import {useStore} from '@nanostores/react'
import ProjectInfo from '../structure/ProjectInfo'
import LayerData from '../structure/LayerData'

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
    taskQueueService: api.TasksQueueServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
    infoService: api.NNInfoServicePromiseClient
    datasetService: api.DatasetServicePromiseClient

    projectId: number
}

function Editor(props: EditorProps): JSX.Element {
    const user = useStore(token)
    const tokenInfo = {'Authorization': 'Bearer ' + user}
    const [projectInfo, setProjectInfo] = useState<ProjectInfo | undefined>(undefined)

    useEffect(() => {
        const request = new api.NNInfoRequest().setNnid(props.projectId)
        props.infoService.getNNInfo(request, tokenInfo).then((result) => {
            setProjectInfo(new ProjectInfo(
                result.getNntype(),
                result.getLayersList().map((layer, id) => new LayerData(id, layer.getNeurons(), layer.getLayertype(),
                    layer.getActivationfunction())),
            ))
        })
    }, [])

    const flexStyle = {display: 'flex'}
    return (
        projectInfo != undefined ? <div style={flexStyle}>
            <LeftPanel projectInfo={projectInfo} taskQueueService={props.taskQueueService}
                versionService={props.versionService} datasetService={props.datasetService}/>
            <EditorWindow projectInfo={projectInfo} versionService={props.versionService}
                modificationService={props.modificationService}/>
            <RightPanel modificationService={props.modificationService}
                versionService={props.versionService}/>
        </div> : <></>
    )
}

export default Editor
