import React, {useState} from 'react';
import Layer from './Layer';


function EditorWindow() {
    const [layers, setLayers] = useState(0)

    return (
        <div className="Editor">
            <div className="Window">
                <h2>Editor Window</h2>
                Layers: {layers}
                <div>
                    <button onClick={() => setLayers(prev => prev - 1)} className="value-button" id="decrease">-</button>
                    <input onChange={e => setLayers(parseInt(e.target.value))} type="number" id="number" value={layers}/>
                    <button onClick={() => setLayers(prev => prev + 1)} className="value-button" id="increase">+</button>
                </div>
                <section>
                    {layers >= 0 ? [...Array(layers).keys()].map(index => <Layer key={index}/>) : ''}
                </section>
            </div>
        </div>
    );
}

export default EditorWindow;


