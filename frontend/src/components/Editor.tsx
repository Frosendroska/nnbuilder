import React, {useEffect, useState} from 'react';
import EditorWindow from './EditorWindow'
import * as api from 'nnbuilder-api'
import {NetworkType} from 'nnbuilder-api';
import LeftPanel from "./LeftPanel";
import {modificationService} from "../clients";
import RightPanel from "./RightPanel";

type EditorProps = {
    modificationService: api.NNModificationServicePromiseClient
}

function Editor(props: EditorProps): JSX.Element {
    const ulStyle = { display: "flex" }
    return (
        <div style={ulStyle}>
            <LeftPanel modificationService={modificationService}/>
            <EditorWindow/>
            <RightPanel modificationService={modificationService}/>
        </div>
    );
}

export default Editor;


