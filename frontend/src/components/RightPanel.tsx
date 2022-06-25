import React from 'react'
import * as api from 'nnbuilder-api'
import './style/Panel.scss'

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function RightPanel(props: EditorProps): JSX.Element {

    const addSnapshot = () => {
       alert("Snapshot added!");
       const request = new api.makeNNSnapshotRequest().setNnid(1)
       props.versionService.makeNNSnapshot(request)
       return true;
    }

    const delSnapshot = () => {
        alert("Snapshot deleted!");
        const request = new api.deleteNNVersionRequest().setNnid(1)
        props.versionService.deleteNNVersion(request)
        return true;
    }

    return (
        <div className='right'>
            <div>
                <input type='submit' value='Undo'/>
                <input type='submit' value='Redo'/>
            </div>
            <div>
                <input type='submit' value='Add snapshot' onClick={addSnapshot}/>
                <input className={'submit-red'} type='submit' value='Delete snapshot' onClick={delSnapshot}/>
            </div>
        </div>
    )
}

export default RightPanel
