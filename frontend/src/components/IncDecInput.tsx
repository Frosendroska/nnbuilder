import React, {useState} from 'react'

function IncDecInput(currentValue: number, setCurrentValue: (arg: number) => void, increment: number, isInteger: boolean) {
    const softValidation = new RegExp('^-?[0-9]*[.]?[0-9]{0,5}$')
    const hardValidation = new RegExp('^[0-9]+([.][0-9]{1,5})?$')
    const [localValue, setLocalValue] = useState(currentValue.toString())

    const setActualValue = (arg: number) => {
        const value = Math.max(0, arg)
        const result = isInteger ? parseInt(value.toFixed(0)) : parseFloat(value.toFixed(5))
        value % 1 === 0 ? setCurrentValue(value) : setCurrentValue(result)
        return result
    }

    const setValueByInput = (arg: string) => {
        if (softValidation.test(arg)) setLocalValue(arg)
        if (hardValidation.test(arg)) {
            setActualValue(Number(arg))
        }
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
            onChange={e => setValueByInput(e.target.value)}
            onBlur={() => setLocalValue(currentValue.toString())}
        />
        <div className={'button'} onClick={() => setValueByButton(currentValue + increment)}>
            <div className={'arrow arrow-up'}/>
        </div>
    </div>
}

export default IncDecInput
