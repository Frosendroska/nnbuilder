import { SetStateAction, useState} from 'react';
import './Form.scss';
import * as api from 'nnbuilder-api'

export default function Form() {

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleName = (e: { target: { value: SetStateAction<string>; }; }) => {
        setName(e.target.value);
    };

    const handleEmail = (e: { target: { value: SetStateAction<string>; }; }) => {
        setEmail(e.target.value);
    };

    const handlePassword = (e: { target: { value: SetStateAction<string>; }; }) => {
        setPassword(e.target.value);
    };

    const handleSubmit = (e: { preventDefault: () => void; }) => {
        e.preventDefault();
    };

    const handleSubmitted = (e: { preventDefault: () => void; }) => {
        alert('A name was submitted: ' + name);
        e.preventDefault();
    }

    return (
        <div className="form">
            <div>
                <h1>User Registration</h1>
            </div>

            <form>
                <label className="label">Name</label>
                <input onChange={handleName} className="input"
                       value={name} type="text" />

                <label className="label">Email</label>
                <input onChange={handleEmail} className="input"
                       value={email} type="email" />

                <label className="label">Password</label>
                <input onChange={handlePassword} className="input"
                       value={password} type="password" />

                <button onClick={handleSubmit} className="btn" type="submit">
                    Submit
                </button>
            </form>
        </div>
    );
}