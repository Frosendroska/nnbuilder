import * as api from 'nnbuilder-api'

export const authService = {
        getSum: jest.fn((request: api.LoginRequest) => Promise.resolve(
           new api.LoginResponse().setToken(`token:${request.getEmail()}:${request.getPassword()}`),
        )),
}

export const sumService = {
    getSum: jest.fn((request: api.GetSumRequest) => Promise.resolve(
        new api.GetSumResponse().setSum(request.getLhs() + request.getRhs()),
    )),
}
