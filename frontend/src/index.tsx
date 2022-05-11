import React from 'react'
import ReactDOM from 'react-dom'
import App from './components/App'
import * as api from './clients'

ReactDOM.render(
    <App authService={api.authService} buildService={api.buildService} modificationService={api.modificationService}/>,
    document.getElementById('root'),
)
