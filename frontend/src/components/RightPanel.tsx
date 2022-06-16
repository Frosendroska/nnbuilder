import React, {useEffect, useState} from 'react';
import * as api from 'nnbuilder-api'
import {NetworkType} from 'nnbuilder-api';

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
}

function RightPanel(props: EditorProps): JSX.Element {
    const [nntype, setNNtype] = useState("FF")
    const [create, setCreate] = useState(false)

    useEffect(() => {
        let type = NetworkType.FF
        if (nntype == 'RNN') type = NetworkType.RNN
        if (nntype == 'LSTM') type = NetworkType.LSTM
        if (nntype == 'CNN') type = NetworkType.CNN
        if (create) props.modificationService.createnn(new api.NNCreationRequest().setNntype(type)).then((value: api.NNCreationResponse) => {
            console.log(JSON.stringify(value))
        })
    }, [create])
    const ulStyle = { display: "flex" }
    return (
        <div className="Left">
            <h1>NNTypes: {nntype}</h1>
            <div><input onChange={e => {
                setNNtype(e.target.value);
                setCreate(false)
            }} type="radio" value="FF" defaultChecked name="type"/> Feed forward
            </div>
            <div><input onChange={e => {
                setNNtype(e.target.value);
                setCreate(false)
            }} type="radio" value="RNN" name="type"/> Recurrent network
            </div>
            <div><input onChange={e => {
                setNNtype(e.target.value);
                setCreate(false)
            }} type="radio" value="LSTM" name="type"/> Long term memory
            </div>
            <div><input onChange={e => {
                setNNtype(e.target.value);
                setCreate(false)
            }} type="radio" value="CNN" name="type"/> Deep convolutional network
            </div>
            <div>
                <button onClick={e => setCreate(true)} className="Create">Create nn</button>
            </div>
            <div>
                <button className="Load">Load dataset</button>
            </div>
            public
            <div>
                <button className="Save">Save neural network</button>
            </div>
            <div>
                <button className="Load">Load learnt model</button>
            </div>
        </div>
    );
}

export default RightPanel;


