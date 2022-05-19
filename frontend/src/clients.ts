import * as api from 'nnbuilder-api'

export const authService = new api.AuthServicePromiseClient(process.env.API_HOST!, null)
export const sumService = new api.SumServicePromiseClient(process.env.API_HOST!, null)
