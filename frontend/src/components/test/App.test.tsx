import React from 'react'
import {render, waitFor} from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from '../App'
import {authService, sumService} from '../../clients'

jest.mock('../../clients')

test('App simple test', async () => {
    const component = render(<App authService={authService} sumService={sumService} />)
    expect(component.getByText(/Computed sum/i).textContent).toBe('Computed sum: ')

    userEvent.type(component.getByLabelText(/lhs/i), '10')
    userEvent.type(component.getByLabelText(/rhs/i), '20')
    userEvent.click(component.getByRole('button', {name: /Compute sum/i}))

    await waitFor(() => expect(component.getByText(/Computed sum/i).textContent).toBe('Computed sum: 30'))
})
