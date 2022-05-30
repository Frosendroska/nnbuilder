import React from 'react';
import './style/Header.scss';
import {token} from "./App";
import { useStore } from '@nanostores/react'

function Projects(): JSX.Element{
    const user = useStore(token)
    return(
        <>
            Hello, {user}, here will be your projects!
        </>
    );
}

export default Projects;