import sys
import numpy as np


def main():
    args = sys.argv[1:]
    print(f'Script called with args: {args}')
    print(np.random.normal(size=100))


if __name__ == '__main__':
    main()
