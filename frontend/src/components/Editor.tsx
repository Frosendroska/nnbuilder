import React, {useEffect, useState} from 'react';
import EditorWindow from './EditorWindow'
import * as api from 'nnbuilder-api'
import { NetworkType } from 'nnbuilder-api';
type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
}
function Editor(props: EditorProps): JSX.Element {
    const [nntype, setNNtype] = useState("FF")

    useEffect(() => {
        let type = NetworkType.FF
        if (nntype == 'RNN') type = NetworkType.RNN
        if (nntype == 'LSTM') type = NetworkType.LSTM
        if (nntype == 'CNN') type = NetworkType.CNN
        props.modificationService.createnn(new api.NNCreationRequest().setNntype(type)).then((value: api.NNBuildingResponse) => {
            console.log(JSON.stringify(value))
        })
    }, [nntype])

    return (
        <div className="Editor">
            <h1>NNTypes: {nntype}</h1>
            <div><input onChange={e => setNNtype(e.target.value)} type="radio" value="FF" defaultChecked name="type"/> Feed forward </div>
            <div><input onChange={e => setNNtype(e.target.value)} type="radio" value="RNN" name="type"/> Recurrent network</div>
            <div><input onChange={e => setNNtype(e.target.value)} type="radio" value="LSTM" name="type"/> Long term memory</div>
            <div><input onChange={e => setNNtype(e.target.value)} type="radio" value="CNN" name="type"/> Deep convolutional network</div>
            <div><button className="Load">Load dataset</button></div>
            <EditorWindow/>
            <div><button className="Save">Save neural network</button></div>
            <div><button className="Load">Load learnt model</button></div>
        </div>
    );
}

export default Editor;


