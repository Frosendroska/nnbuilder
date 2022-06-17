import React from 'react'
import EditorWindow from './EditorWindow'
import * as api from 'nnbuilder-api'
import LeftPanel from './LeftPanel'
import RightPanel from './RightPanel'

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
}

function Editor(props: EditorProps): JSX.Element {
    const flexStyle = {display: 'flex'}
    return (
        <div style={flexStyle}>
            <LeftPanel/>
            <EditorWindow/>
            <RightPanel modificationService={props.modificationService}/>
        </div>
    )
}

export default Editor
