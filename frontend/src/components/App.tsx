import React, {useCallback, useState} from 'react'
import * as api from 'nnbuilder-api'
import Form from "./Form"
import Editor from "./Editor"

type AppProps = {
    authService: api.AuthServicePromiseClient
    buildService: api.NNBuilderServicesPromiseClient
    modificationService: api.NNModificationServicesPromiseClient
}

export default function App(props: AppProps): JSX.Element {
    return (
        <>
        <Form authService={props.authService} />
        <Editor buildService={props.buildService} modificationService = {props.modificationService}/>
        </>
    );
}
