import * as api from 'nnbuilder-api'

export const authService = new api.AuthServicePromiseClient(process.env.API_HOST!, null)
export const modificationService = new api.NNModificationServicePromiseClient(process.env.API_HOST!, null)
export const taskQueueService = new api.TasksQueueServicePromiseClient(process.env.API_HOST!, null)
export const versionService = new api.NNVersionServicePromiseClient(process.env.API_HOST!, null)
export const userAccountService = new api.UserAccountServicePromiseClient(process.env.API_HOST!, null)
export const infoService = new api.NNInfoServicePromiseClient(process.env.API_HOST!, null)
