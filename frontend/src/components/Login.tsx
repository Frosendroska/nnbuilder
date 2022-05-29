import { useForm } from "react-hook-form";
import * as api from 'nnbuilder-api'
import { token } from './App';
import {useNavigate} from "react-router";

type FormProps = {
    authService: api.AuthServicePromiseClient
}

type FormValues = {
    email: string;
    password: string;
};

function Login(props: FormProps): JSX.Element{

    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm<FormValues>({
        defaultValues: {
            email: "",
            password: "",
        }
    });

    const sendRequest = (handleSubmit((data) => {
        const request = new api.LoginRequest()
            .setEmail(data.email)
            .setPassword(data.password)
        props.authService.login(request).then((value: api.LoginResponse) => {
            token.set(value.getToken())
            console.log(token.get())
            console.log(value.getException())
        })
        console.log(data)
        navigate('/projects');
    }));


    return (
        <div className="form-center">
            <form className="form" onSubmit={sendRequest}>
                <h1>User Login</h1>
                Email
                <input type="text"
                       {...register("email", {
                           required: {
                               value: true,
                               message: "this is required"
                           }
                       })}
                       name="email"
                       placeholder="ivanivanov@gmail.com"
                />
                Password
                <input type="password"
                       {...register("password", {
                           minLength: {
                               value: 6,
                               message: "Password min length is 6"
                           },
                           required: "please enter email",
                       })}
                       name="password"
                       placeholder="qwerty123"
                />
                <input type="submit" />
            </form>
        </div>
    )
}

export default Login;