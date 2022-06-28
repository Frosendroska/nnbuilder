import React, {useEffect, useState} from 'react'

function incDecInput(currentValue: number, setCurrentValue: (arg: number) => void, increment: number,
    isInteger = true, min = 0, max = Infinity) {
    const softValidation = new RegExp('^-?[0-9]*[.]?[0-9]{0,5}$')
    const hardValidation = new RegExp('^[0-9]+([.][0-9]{1,5})?$')
    const [localValue, setLocalValue] = useState(currentValue.toString())

    useEffect(() => {
        console.log(currentValue)
        setLocalValue(currentValue.toString())
    }, [currentValue]);

    function correctValue(arg: number) {
        return Math.min(max, Math.max(min, arg))
    }

    const setActualValue = (arg: number) => {
        const value = correctValue(arg)
        const result = isInteger ? parseInt(value.toFixed(0)) : parseFloat(value.toFixed(5))
        value % 1 === 0 ? setCurrentValue(value) : setCurrentValue(result)
        return result
    }

    const setValueByInput = (arg: string) => {
        if (hardValidation.test(arg)) {
            setActualValue(Number(arg))
            setLocalValue(correctValue(Number(arg)).toString())
        } else if (softValidation.test(arg)) setLocalValue(arg)
    }

    const setValueByButton = (arg: number) => {
        const result = setActualValue(arg)
        setLocalValue(result.toString())
    }

    return <div className='IncDecInput'>
        <div className={'button'} onClick={() => setValueByButton(currentValue - increment)}>
            <div className={'arrow arrow-down'}/>
        </div>
        <input
            value={localValue}
            onChange={(e) => setValueByInput(e.target.value)}
            onBlur={() => setLocalValue(currentValue.toString())}
        />
        <div className={'button'} onClick={() => setValueByButton(currentValue + increment)}>
            <div className={'arrow arrow-up'}/>
        </div>
    </div>
}

export default incDecInput
