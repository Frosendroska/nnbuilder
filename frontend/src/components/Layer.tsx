import React, { useState } from 'react';

const options = [
    { value: 'C++', label: 'C++' },
    { value: 'JAVA', label: 'JAVA' },
    { value: 'Javascript', label: 'Javascript' },
    { value: 'Python', label: 'Python' },
    { value: 'Swift', label: 'Swift' }
];

function Layer() {
    const [neurons, setNeurons] = useState(0)
    const [actFunc, setActFunc] = useState<string | undefined>()

    return (
        <div className="Layer">
            <h3>Layer</h3>
            <div>
                <button onClick={() => setNeurons(prev => prev - 1)}  className="value-button" id="decrease">-</button>
                <input onChange={e => setNeurons(parseInt(e.target.value))}type="number" id="number" value={neurons}/>
                <button onClick={() => setNeurons(prev => prev + 1)}  className="value-button" id="increase">+</button>
            </div>

            <select name="select" defaultValue={'value1'}>
                <option value="value1">sin</option>
                <option value="value2">cos</option>
                <option value="value3">tg</option>
            </select>
        </div>
    );
}

export default Layer;


