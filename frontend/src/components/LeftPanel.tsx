import React, {useEffect, useState} from 'react';
import * as api from 'nnbuilder-api'
import {NetworkType} from 'nnbuilder-api';
import './style/Form.scss';

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
}

function LeftPanel(props: EditorProps): JSX.Element {
    const [nntype, setNNtype] = useState("FF")
    const [status, setStatus] = useState("not trained")
    const [target, setTarget] = useState("pet type")

    const ulStyle = { display: "flex" }
    return (
            <div className="Left">
                <h2>NNType: {nntype}</h2>
                <h2>Status: {status}</h2>
                <h3>Epochs</h3>
                <div style={ulStyle}>
                <input type="button" value="<"/>
                <input
                    type="text"
                    //value={this.state.value}
                    //onChange={this.handleChange}
                />
                    <input type="button" value=">"/>
                </div>
                <h3>Learning rate</h3>
                <input
                    type="text"
                    //value={this.state.value}
                    //onChange={this.handleChange}
                />
                <h3>Target: {target}</h3>
                <input type="submit" value="Load dataset"/>
                <input type="submit" value="Train"/>
            </div>
    );
}

export default LeftPanel;


