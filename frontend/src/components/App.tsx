import React, {useCallback, useState} from 'react'
import * as api from 'nnbuilder-api'

type AppProps = {
    authService: api.AuthServicePromiseClient
    sumService: api.SumServicePromiseClient
}

export default function App(props: AppProps): JSX.Element {
    const [username, setUsername] = useState<string | undefined>()
    const [password, setPassowrd] = useState<string | undefined>()
    const [token, setToken] = useState<string | undefined>()

    const [lhs, setLhs] = useState<number | undefined>()
    const [rhs, setRhs] = useState<number | undefined>()
    const [sum, setSum] = useState<number | undefined>()

    const onLoginSubmit = useCallback((event) => {
        event.preventDefault()
        if (username === undefined || password === undefined) return
        const request = new api.LoginRequest()
            .setEmail(username)
            .setPassword(password)
        props.authService.login(request).then((value: api.LoginResponse) => {
            setToken(value.getToken())
        })
    }, [username, password])

    const onSumSubmit = useCallback((event) => {
        event.preventDefault()
        const bearerToken = token ? `Bearer ${token}` : ''
        console.log(bearerToken)

        if (lhs === undefined || rhs === undefined) return
        const request = new api.GetSumRequest()
            .setLhs(lhs)
            .setRhs(rhs)
        props.sumService.getSum(request, {Authorization: bearerToken}).then((value: api.GetSumResponse) => {
            setSum(value.getSum())
        })
    }, [lhs, rhs])

    return (
        <div>
            <h1>Login:</h1>
            <form onSubmit={onLoginSubmit}>
                <label>
                    email:
                    <input
                        value={username || ''}
                        onChange={(event) => setUsername(event.target.value)}
                    />
                </label>
                <br />
                <label>
                    password:
                    <input
                        type="password"
                        value={password || ''}
                        onChange={(event) => setPassowrd(event.target.value)}
                    />
                </label>
                <br />
                <input type="submit" value="Login" />
            </form>
            <h2>Token: {token}</h2>

            <h1>Sum Calculator</h1>
            <form onSubmit={onSumSubmit}>
                <label>
                    lhs:
                    <input
                        type="number"
                        value={lhs || ''}
                        onChange={(event) => setLhs(parseInt(event.target.value))}
                    />
                </label>
                <br />
                <label>
                    rhs:
                    <input
                        type="number"
                        value={rhs || ''}
                        onChange={(event) => setRhs(parseInt(event.target.value))}
                    />
                </label>
                <br />
                <input type="submit" value="Compute sum" />
            </form>
            <h2>Computed sum: {sum}</h2>
        </div>
    )
}
