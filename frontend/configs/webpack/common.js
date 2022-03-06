// shared config (dev and prod)
const {resolve} = require('path');
const BundleTracker = require('webpack-bundle-tracker');
const webpack = require('webpack');
const dotenv = require('dotenv');

const parseConfigs = configs => Object.keys(configs || {}).reduce(
    (acc, val) => ({ ...acc, [val]: JSON.stringify(configs[val]) }),
    {},
);

module.exports = {
  resolve: {
    extensions: ['.ts', '.tsx', '.js', '.jsx'],
  },
  context: resolve(__dirname, '../../src'),
  output: {
    assetModuleFilename: 'images/[fullhash][ext][query]',
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        use: ['babel-loader', 'source-map-loader'],
        exclude: /node_modules/,
      },
      {
        test: /\.tsx?$/,
        use: ['babel-loader', 'ts-loader'],
      },
      {
        test: /\.css$/,
        use: ['style-loader', { loader: 'css-loader', options: { importLoaders: 1 } }],
      },
      {
        test: /\.scss$/,
        use: [
          { loader: 'style-loader' },
          { loader: 'css-loader', options: { importLoaders: 1 } },
          { loader: 'sass-loader' },
        ]
      },
      {
        test: /\.(jpe?g|png|gif|svg)$/i,
        type: 'asset/resource',
      },
    ],
  },
  plugins: [
    new BundleTracker({filename: './webpack-stats.json'}),

    new webpack.DefinePlugin({
      'process.env': {
        ...parseConfigs(dotenv.config({
          path: '../.env'
        })),
        ...parseConfigs(process.env)
      }
    }),
  ],
  externals: {
    'react': 'React',
    'react-dom': 'ReactDOM',
  },
  performance: {
    hints: false,
  },
};
