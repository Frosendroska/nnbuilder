// development config
const merge = require('webpack-merge');
const webpack = require('webpack');
const commonConfig = require('./common');
const {resolve} = require('path');
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = merge.merge(commonConfig, {
  mode: 'development',
  entry: [
    'react-hot-loader/patch', // activate HMR for React
    'webpack-dev-server/client?http://localhost:8080/dist/webpack_bundles/',// bundle the client for webpack-dev-server and connect to the provided endpoint
    'webpack/hot/only-dev-server', // bundle the client for hot reloading, only- means to only hot reload for successful updates
    './index.tsx' // the entry point of our app
  ],
  output: {
    filename: 'bundle.[fullhash].js',
    path: resolve(__dirname, '../../dist/webpack_bundles/'),
    // publicPath: '/',
  },
  devServer: {
    hot: true, // enable HMR on the server,
    historyApiFallback: true,
    headers: {
      'Access-Control-Allow-Origin': '*'
    },
  },
  devtool: 'eval-cheap-module-source-map',
  plugins: [
    new HtmlWebpackPlugin({template: 'index.html.ejs',}),
    new webpack.HotModuleReplacementPlugin(), // enable HMR globally
  ],
  optimization: {
    moduleIds: 'named',
  }
});
