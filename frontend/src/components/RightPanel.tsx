import React, {useState} from 'react'
import * as api from 'nnbuilder-api'
import './style/Panel.scss'
import versionController from './VersionController'
import {currentVersion} from './App'
import {useStore} from '@nanostores/react'

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
    versionService: api.NNVersionServicePromiseClient
}

function RightPanel(props: EditorProps): JSX.Element {
    const [version, setVersion] = useState(1)
    const maxValue = version
    const nnId = useStore(currentVersion)

    const addSnapshot = () => {
        console.log(nnId)
        alert('Snapshot added!')
        const request = new api.MakeNNSnapshotRequest().setNnid(Number(nnId))
        props.versionService.makeNNSnapshot(request)
        setVersion(version+1)
        return true
    }

    const delSnapshot = () => {
        alert('Snapshot deleted!')
        const request = new api.DeleteNNVersionRequest().setNnid(Number(nnId))
        props.versionService.deleteNNVersion(request)
        setVersion(version-1)
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
