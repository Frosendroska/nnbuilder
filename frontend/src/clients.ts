import * as api from 'nnbuilder-api'

export const authService = new api.AuthServicePromiseClient(process.env.API_HOST!, null)
export const buildService = new api.NNBuilderServicesPromiseClient(process.env.API_HOST!, null)
export const modificationService = new api.NNModificationServicesPromiseClient(process.env.API_HOST!, null)
