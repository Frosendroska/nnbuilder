import {useForm} from 'react-hook-form'
import * as api from 'nnbuilder-api'
import '@babel/polyfill'

import {useNavigate} from 'react-router'
import {token} from './App'
import React, {useState} from 'react'

type FormProps = {
    authService: api.AuthServicePromiseClient
}

type FormValues = {
    firstName: string
    lastName: string
    email: string
    password: string
}

function Register(props: FormProps): JSX.Element {
    const [error, setError] = useState<string>('')

    const navigate = useNavigate()

    const {
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<FormValues>({
        defaultValues: {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
        },
    })

    const sendRequest = (handleSubmit(async (data) => {
        setError('')
        const request = new api.RegisterRequest()
            .setName(data.lastName != '' ? data.firstName + '' + data.lastName : data.firstName)
            .setEmail(data.email)
            .setPassword(data.password)
        const result = await props.authService.register(request)
        const newError = result.getException()
        setError(newError)
        if (newError == '') {
            const logrequest = new api.LoginRequest()
                .setEmail(data.email)
                .setPassword(data.password)
            props.authService.login(logrequest).then((result: api.LoginResponse) => {
                token.set(result.getToken())
                console.log(token.get())
                setError(result.getException())
            })
            navigate('/projects')
        }
    }))


    console.log(errors)
    return (
        <div>
            <form className='form' onSubmit={sendRequest}>
                {error ? <p className='invalid-feedback'>{error}</p> : null}
                <h1>User Registration</h1>
                <div>
                    First Name
                    <input type='text'
                        className={`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                        {...register('firstName', {
                            required: {
                                value: true,
                                message: 'First name is required',
                            },
                        })}
                        placeholder='Ivan'
                    />
                    <div className='invalid-feedback'>{errors.firstName?.message}</div>
                </div>
                <div>Last Name
                    <input type='text'
                        {...register('lastName', {})}
                        name='lastName'
                        placeholder='Ivanov'
                    />
                    <div className='invalid-feedback'>{errors.lastName?.message}</div>
                </div>
                <div>Email
                    <input type='text'
                        {...register('email', {
                            required: {
                                value: true,
                                message: 'Email is required',
                            },
                        })}
                        name='email'
                        placeholder='ivanivanov@gmail.com'
                    />
                    <div className='invalid-feedback'>{errors.email?.message}</div>
                </div>
                <div>Password
                    <input type='password'
                        {...register('password', {
                            minLength: {
                                value: 6,
                                message: 'Password min length is 6',
                            },
                            required: 'Password is required',
                        })}
                        name='password'
                        placeholder='qwerty123'
                    />
                    <div className='invalid-feedback'>{errors.password?.message}</div>
                </div>
                <input type='submit'/>
            </form>
        </div>
    )
}

export default Register
