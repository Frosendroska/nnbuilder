import React, {useCallback, useState} from 'react'
import * as api from 'nnbuilder-api'

type AppProps = {
    sumService: api.SumServicePromiseClient
}

export default function App(props: AppProps): JSX.Element {
    const [sum, setSum] = useState<number | undefined>()
    const [lhs, setLhs] = useState<number | undefined>()
    const [rhs, setRhs] = useState<number | undefined>()

    const onSubmit = useCallback((event) => {
        event.preventDefault()

        if (lhs === undefined || rhs === undefined) return
        const request = new api.GetSumRequest()
            .setLhs(lhs)
            .setRhs(rhs)
        props.sumService.getSum(request).then((value: api.GetSumResponse) => {
            setSum(value.getSum())
        })
    }, [lhs, rhs])

    return (
        <div>
            <h1>Sum Calculator</h1>
            <form onSubmit={onSubmit}>
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
