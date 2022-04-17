import React from 'react'
import ReactDOM from 'react-dom'
import App from './components/App'
import * as api from './clients'
import Form from './components/Form'

ReactDOM.render(
    //<App sumService={api.sumService} />,
    <Form />,
    document.getElementById('root'),
)