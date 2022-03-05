import * as api from 'nnbuilder-api';
import React, {useEffect, useState} from 'react';

let client = new api.SumServicePromiseClient("", null);

type AppProps = {};

export default function App(_props: AppProps): JSX.Element {
    const [sum, setSum] = useState<number | undefined>(undefined);

    useEffect(() => {
        let request = new api.GetSumRequest().setLhs(1).setRhs(2)
        client.getSum(request).then((value: api.GetSumResponse) => setSum(value.getSum))
    }, [])

    return <h1>{sum}</h1>;
}
