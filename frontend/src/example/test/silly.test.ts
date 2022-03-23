import {sillyFunction} from '../silly'

describe('silly function', () => {
    test('guaranteed random', () => {
        expect(sillyFunction()).toBe(4)
    })
})
