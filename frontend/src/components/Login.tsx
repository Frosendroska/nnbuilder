import {useForm} from 'react-hook-form'
import * as api from 'nnbuilder-api'
import {token} from './App'
import {useNavigate} from 'react-router'
import React, {useState} from 'react'

type FormProps = {
    authService: api.AuthServicePromiseClient
}

type FormValues = {
    email: string
    password: string
}

function Login(props: FormProps): JSX.Element {
    const [error, setError] = useState<string>('')

    const navigate = useNavigate()

    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<FormValues>({
        defaultValues: {
            email: '',
            password: '',
        }
    })

    const sendRequest = (handleSubmit((data) => {
        const request = new api.LoginRequest()
            .setEmail(data.email)
            .setPassword(data.password)
        props.authService.login(request).then((result: api.LoginResponse) => {
            token.set(result.getToken())
            let newError = result.getException()
            setError(newError)
            if (newError == '') {
                navigate('/projects')
            }
        })
    }))


    return (
        <div className='form-center'>
            <form className='form' onSubmit={sendRequest}>
                <h1>User Login</h1>
                {error ? <p className='invalid-feedback'>{error}</p> : null}
                <div>
                    Email
                    <input type='text'
                           {...register('email', {
                               required: {
                                   value: true,
                                   message: 'Please enter email'
                               }
                           })}
                           name='email'
                           placeholder='ivanivanov@gmail.com'
                    />
                    <div className='invalid-feedback'>{errors.email?.message}</div>
                </div>
                <div>
                    Password
                    <input type='password'
                           {...register('password', {
                               minLength: {
                                   value: 6,
                                   message: 'Password min length is 6'
                               },
                               required: 'Please enter password',
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

export default Login
