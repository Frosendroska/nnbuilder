import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter} from 'react-router-dom'
import App from './components/App'
import * as api from './clients'

ReactDOM.render(
    <BrowserRouter>
        <App authService={api.authService} modificationService={api.modificationService} taskQueueService={api.taskQueueService} versionService={api.versionService} userAccountService = {api.userAccountService}/>
    </BrowserRouter>,
    document.getElementById('root'),
)
