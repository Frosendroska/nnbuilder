import * as api from 'nnbuilder-api'

export const sumService = new api.SumServicePromiseClient(process.env.API_HOST!, null)
