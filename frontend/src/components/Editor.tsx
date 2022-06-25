import React from 'react'
import EditorWindow from './EditorWindow'
import * as api from 'nnbuilder-api'
import LeftPanel from './LeftPanel'
import RightPanel from './RightPanel'

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
    taskQueueService: api.TasksQueueServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function Editor(props: EditorProps): JSX.Element {
    const flexStyle = {display: 'flex'}
    return (
        <div style={flexStyle}>
            <LeftPanel taskQueueService={props.taskQueueService} versionService={props.versionService}/>
            <EditorWindow versionService={props.versionService} modificationService={props.modificationService}/>
            <RightPanel modificationService={props.modificationService} versionService={props.versionService}/>
        </div>
    )
}

export default Editor
