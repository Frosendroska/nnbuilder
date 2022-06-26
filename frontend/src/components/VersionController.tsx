import React, {useState} from 'react'

function VersionController(currentValue: number, setCurrentValue: (arg: number) => void, increment: number,
                           isInteger: boolean = true, min: number = 0, max: number = Infinity) {
    const [localValue, setLocalValue] = useState(currentValue.toString())

    function correctValue(arg: number) {
        return Math.min(max, Math.max(min, arg))
    }

    const setActualValue = (arg: number) => {
        const value = correctValue(arg)
        const result = isInteger ? parseInt(value.toFixed(0)) : parseFloat(value.toFixed(5))
        value % 1 === 0 ? setCurrentValue(value) : setCurrentValue(result)
        return result
    }

    const setValueByButton = (arg: number) => {
        const result = setActualValue(arg)
        setLocalValue(result.toString())
    }

    return <div className='version-controller'>
        <div className={'button'} onClick={() => setValueByButton(currentValue - increment)}>
            <div className={'arrow arrow-down'}/>
        </div>
        <div className={"version-value"}>
            <div>Version<br/>
                {localValue}/{max}</div>
        </div>
        <div className={'button'} onClick={() => setValueByButton(currentValue + increment)}>
            <div className={'arrow arrow-up'}/>
        </div>
    </div>
}

export default VersionController
