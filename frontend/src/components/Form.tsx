import {SetStateAction, useCallback, useState} from 'react';
import * as api from 'nnbuilder-api'

type FormProps = {
    authService: api.AuthServicePromiseClient
}

function Form(props: FormProps): JSX.Element{

    const [name, setUsername] = useState<string | undefined>();
    const [email, setToken] = useState<string | undefined>();
    const [password, setPassword] = useState<string | undefined>();

    const onLoginSubmit = useCallback((event) => {
        event.preventDefault()
        if (name === undefined || password === undefined || email == undefined) return

        const request = new api.LoginRequest()
            .setEmail(name)
            .setPassword(password)
        alert("name submitted" + name)
        props.authService.login(request).then((value: api.LoginResponse) => {
            setToken(value.getToken())
        })
    }, [name, password])

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
    };

    return (
        <div className="form">
            <div>
                <h1>User Registration</h1>
            </div>

            <form>
                <label className="label">Name</label>
                <input onChange={onLoginSubmit} className="input"
                       value={name} type="text" />

                <label className="label">Email</label>
                <input onChange={onLoginSubmit} className="input"
                       value={email} type="email" />

                <label className="label">Password</label>
                <input onChange={onLoginSubmit} className="input"
                       value={password} type="password" />

                <button onClick={onLoginSubmit} className="btn" type="submit">
                    Submit
                </button>
            </form>
        </div>
    );
}

export default Form;