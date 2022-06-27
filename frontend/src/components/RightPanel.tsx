import React, {useState} from 'react'
import * as api from 'nnbuilder-api'
import './style/Panel.scss'
import versionController from './VersionController'

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function RightPanel(props: EditorProps): JSX.Element {
    const [version, setVersion] = useState(1)
    const maxValue = 5

    const addSnapshot = () => {
        alert('Snapshot added!')
        const request = new api.MakeNNSnapshotRequest().setNnid(1)
        props.versionService.makeNNSnapshot(request)
        return true
    }

    const delSnapshot = () => {
        alert('Snapshot deleted!')
        const request = new api.DeleteNNVersionRequest().setNnid(1)
        props.versionService.deleteNNVersion(request)
        return true
    }

    const ulStyle = {display: 'flex', gap: '10px'}

    return (
        <div className='right'>
            {versionController(version, setVersion, 1, true, 1, maxValue)}
            <div style={ulStyle}>
                <input className={'submit-grey'} type='submit' value='Undo'/>
                <input className={'submit-grey'} type='submit' value='Redo'/>
            </div>
            <div className={'vertical-panel'}>
                <input type='submit' value='Add snapshot' onClick={addSnapshot}/>
                <input className={'submit-red'} type='submit' value='Delete snapshot' onClick={delSnapshot}/>
            </div>
        </div>
    )
}

export default RightPanel
