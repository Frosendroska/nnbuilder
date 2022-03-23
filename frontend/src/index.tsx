import React from 'react'
import ReactDOM from 'react-dom'
import App from './components/App'
import * as api from './clients'

ReactDOM.render(
    <App sumService={api.sumService} />,
    document.getElementById('root'),
)
