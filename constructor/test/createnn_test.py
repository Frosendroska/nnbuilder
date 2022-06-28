import test_tools as tools
import generate_nndescription


def test_ff_regression():
    info = generate_nndescription.ff_regression()
    data = tools.generate_regression_dataset()


def test_ff_classification():
    info = generate_nndescription.ff_classification()
    data = tools.generate_classification_dataset()


def test_RNN():
    info = generate_nndescription.RNN()
    data = tools.generate_regression_dataset()


def test_LSTM():
    info = generate_nndescription.LSTM()
    data = tools.generate_regression_dataset()


def test_save_and_restore():
    info = generate_nndescription.ff_regression()
    data = tools.generate_regression_dataset()

