import * as api from 'nnbuilder-api'

export const sumService = {
    getSum: jest.fn((request: api.GetSumRequest) => Promise.resolve(
        new api.GetSumResponse().setSum(request.getLhs() + request.getRhs()),
    )),
}
